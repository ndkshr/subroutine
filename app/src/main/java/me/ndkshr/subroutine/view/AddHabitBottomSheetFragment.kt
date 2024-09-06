package me.ndkshr.subroutine.view

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import me.ndkshr.subroutine.R
import me.ndkshr.subroutine.databinding.FragmentAddHabitBottomSheetBinding
import me.ndkshr.subroutine.modal.HabitDataItem
import me.ndkshr.subroutine.modal.HabitLabels
import me.ndkshr.subroutine.notification.AlarmReceiver
import me.ndkshr.subroutine.notification.HABIT_ITEM
import me.ndkshr.subroutine.utlis.OND_DAY_IN_MILLIS
import me.ndkshr.subroutine.utlis.OND_MIN_IN_MILLIS
import me.ndkshr.subroutine.viewmodel.MainActivityViewModel
import me.ndkshr.subroutine.viewmodel.MainActivityViewModelFactory
import java.time.LocalDateTime
import java.util.Calendar
import java.util.UUID


class AddHabitBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var labelsAdapter: LabelsAdapter
    private lateinit var binding: FragmentAddHabitBottomSheetBinding
    private val viewModel: MainActivityViewModel by viewModels(ownerProducer = { requireActivity() }) { MainActivityViewModelFactory() }
    private var curDateTime: LocalDateTime? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_habit_bottom_sheet,
            container,
            false
        )
        isCancelable = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                dialog.behavior.peekHeight = sheet.height
                sheet.parent.parent.requestLayout()
            }
        }

        binding.labelsRv.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        labelsAdapter = LabelsAdapter().apply {
            colors = HabitLabels.entries.toList()
        }

        binding.labelsRv.adapter = labelsAdapter

            binding.close.setOnClickListener {
            dismiss()
        }

        binding.timePickerBtn.setOnClickListener {
            showTimePicker()
        }

        binding.save.setOnClickListener {
            val newHabit = HabitDataItem(
                habitUUID = UUID.randomUUID().toString(),
                habitName = binding.habitNameTv.text.toString(),
                habitLabel = labelsAdapter.selectedLabel.toString(),
                habitReminderTs = curDateTime?.toString()?.split("T")?.get(1) ?: "NULL"
            )

            if (isFormValid()) {
                viewModel.addHabit(newHabit)
                if (newHabit.habitReminderTs != "NULL") {
                    setPeriodicAlarm(newHabit)
                }
                dismiss()
            } else {
                val ctw = ContextThemeWrapper(requireActivity(), R.style.CustomSnackbarTheme)
                val errorSnackBar = Snackbar.make(ctw, binding.root, "Missing Values!", Snackbar.LENGTH_SHORT)
                errorSnackBar.show()
            }
        }
    }

    private fun showTimePicker() {
        curDateTime = LocalDateTime.now()
        val picker = MaterialTimePicker.Builder()
            .setTheme(R.style.ThemeOverlay_App_TimePicker)
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(curDateTime?.hour!!)
            .setNegativeButtonText("Clear Reminder")
            .setPositiveButtonText("Set Reminder")
            .setMinute(curDateTime?.minute!!)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setTitleText("Set a time for daily reminder.")
            .build()

        picker.addOnPositiveButtonClickListener {
            curDateTime = LocalDateTime.of(LocalDateTime.now().year, LocalDateTime.now().month, LocalDateTime.now().dayOfMonth ,picker.hour, picker.minute)
            val suffix = if (picker.hour > 12) "PM" else "AM"
            val hour = String.format("%02d", if (picker.hour > 12) picker.hour - 12 else picker.hour)
            val minute = String.format("%02d", picker.minute)
            binding.timePickerBtn.text = "Reminder:  $hour:$minute $suffix"
        }

        picker.addOnNegativeButtonClickListener {
            binding.timePickerBtn.text = "Pick a time for daily reminder"
        }

        picker.show(parentFragmentManager, "Time Picker")
    }

    private fun setPeriodicAlarm(habitDataItem: HabitDataItem) {
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra(HABIT_ITEM, habitDataItem)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            habitDataItem.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar: Calendar = Calendar.getInstance()

        // Calendar.set(int year, int month, int day, int hourOfDay, int minute, int second)

        // Calendar.set(int year, int month, int day, int hourOfDay, int minute, int second)
        val h = habitDataItem.habitReminderTs.split(":")[0].toInt()
        val m = habitDataItem.habitReminderTs.split(":")[1].toInt()
        calendar.set(
            LocalDateTime.now().year, LocalDateTime.now().monthValue - 1, LocalDateTime.now().dayOfMonth,
            h, m, 0
        )
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            OND_DAY_IN_MILLIS,
            pendingIntent
        )
    }

    private fun isFormValid(): Boolean {
        val hasName = binding.habitNameTv.text?.isNotBlank() ?: false
        return hasName
    }
}