package com.app.ichsanulalifwan.tasklist.ui.addtask

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.ichsanulalifwan.tasklist.R
import com.app.ichsanulalifwan.tasklist.data.entity.TaskEntity
import com.app.ichsanulalifwan.tasklist.databinding.FragmentTaskAddBinding
import com.app.ichsanulalifwan.tasklist.utils.Utils
import com.app.ichsanulalifwan.tasklist.viewmodel.ViewModelFactory
import java.util.*

class AddTaskFragment : Fragment() {

    private lateinit var viewModel: AddTaskViewModel
    private var selectedDate: Long = 0
    private var task: TaskEntity? = null
    private var _binding: FragmentTaskAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskAddBinding.inflate(inflater, container, false)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[AddTaskViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        saveTask()
    }

    private fun initView() {
        binding.run {

            addDate.isEnabled = false

            btnDate.setOnClickListener { pickDateTime() }

            buttonSave.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun pickDateTime() {
        val dateTime = Calendar.getInstance()
        val startYear = dateTime.get(Calendar.YEAR)
        val startMonth = dateTime.get(Calendar.MONTH)
        val startDay = dateTime.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, year, month, day ->
            val selectedDateTime = Calendar.getInstance()
            selectedDateTime.set(year, month, day)
            selectedDate = selectedDateTime.timeInMillis
            val showDateTime = Utils.dateFormatter(selectedDateTime.timeInMillis)
            binding.addDate.setText(showDateTime)
        }, startYear, startMonth, startDay).show()
    }

    private fun saveTask() {
        binding.buttonSave.setOnClickListener {
            showLoading(true)
            if (!checkInput()) {
//                checkInputChanged()
                viewModel.apply {
                    addTodo(populateData())
                    observableStatus.observe(viewLifecycleOwner) {
                        check(it)
                    }
                }
            } else showToast(getString(R.string.empty_data_message))
        }
    }

    private fun checkInput(): Boolean {
        var checkEmpty = false
        val errorText = getString(R.string.error_empty_data)
        val errorDate = getString(R.string.error_empty_date)
        binding.run {
            val text = edAddTask.text.toString()
            val date = addDate.text.toString()

            when {
                text.isEmpty() -> {
                    showLoading(false)
                    checkEmpty = true
                    taskNameInput.requestFocus()
                    taskNameInput.error = errorText
                }
                date.isEmpty() -> {
                    showLoading(false)
                    checkEmpty = true
                    taskDateInput.requestFocus()
                    taskDateInput.error = errorDate
                }
            }
            checkInputChanged()

            return checkEmpty
        }
    }

    private fun populateData(): TaskEntity {
        val id = (if (task != null) task?.id else null)
        return TaskEntity(
            id,
            binding.edAddTask.text.toString(),
            selectedDate,
            id
        )
    }

    private fun checkInputChanged() {
        binding.run {
            edAddTask.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    taskNameInput.error = null
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            addDate.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    taskDateInput.error = null
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun check(status: Boolean) {
        when (status) {
            true -> {
                showLoading(false)
                findNavController().popBackStack()
                showToast("Task Added Successfully")
            }
            false -> {
                showLoading(false)
                showToast("Task Add Failed")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    /**
     * Change the visibility of progressBar
     * true --> Show progressBar
     * false --> Hide progressBar
     */
    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE
        else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}