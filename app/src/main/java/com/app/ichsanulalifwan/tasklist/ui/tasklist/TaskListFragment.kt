package com.app.ichsanulalifwan.tasklist.ui.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ichsanulalifwan.tasklist.R
import com.app.ichsanulalifwan.tasklist.data.entity.TaskEntity
import com.app.ichsanulalifwan.tasklist.databinding.FragmentTaskListBinding
import com.app.ichsanulalifwan.tasklist.ui.adapter.TaskAdapter
import com.app.ichsanulalifwan.tasklist.viewmodel.ViewModelFactory

class TaskListFragment : Fragment() {

    private lateinit var viewModel: TaskListViewModel
    private lateinit var taskAdapter: TaskAdapter
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    private var countItemLeft = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)

        // Init viewModel
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[TaskListViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init adapter
        taskAdapter = TaskAdapter()

        initView()
        setupRecyclerView()
        initData()
        showLoading(true)
    }

    private fun initView() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun setupRecyclerView() {
        binding.rvTaskList.run {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
            setHasFixedSize(true)
        }
//        taskAdapter.setData(dataDummy())
    }

    private fun initData() {
        viewModel.getAllTask().observe(viewLifecycleOwner) { tasks ->
            tasks?.let {
                showLoading(false)
                if (tasks.isEmpty()) showEmptyState()
                else taskAdapter.setData(tasks)
                checkTask(tasks)
                markAllComplete(tasks)
            } ?: showEmptyState()
        }
    }

    private fun checkTask(tasks: List<TaskEntity>) {
        var completedItems = 0
        tasks.forEach { taskEntity ->
            if (taskEntity.done) completedItems += 1
            else countItemLeft += 1
        }

        binding.run {
            tvItemsLeft.text = getString(R.string.text_items_left, countItemLeft)
            btnClearTask.text = getString(R.string.text_clear_completed_task, completedItems)
            btnClearTask.isClickable = completedItems != 0
        }
    }

    private fun markAllComplete(tasks: List<TaskEntity>) {
        val updatedTask = ArrayList<TaskEntity>()
        binding.checkForAll.setOnCheckedChangeListener { _, state ->
            if (state) {
                if (countItemLeft != 0) {
                    showLoading(true)

                    tasks.forEach { task ->
                        task.done = true
                        updatedTask.add(task)
                    }

                    updateAllTask(updatedTask)
                } else showToast("All tasks already completed")
            }
        }
    }

    private fun updateAllTask(tasks: List<TaskEntity>) {
        viewModel.apply {
            updateAllTask(tasks)
            observableEditStatus.observe(viewLifecycleOwner) {
                showLoading(false)
                showToast("All tasks have been completed")
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showEmptyState() {
        binding.emptyText.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}