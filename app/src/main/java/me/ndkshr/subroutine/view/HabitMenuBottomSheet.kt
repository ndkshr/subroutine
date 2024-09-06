package me.ndkshr.subroutine.view

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.ndkshr.subroutine.R
import me.ndkshr.subroutine.databinding.HabitMenuBottomSheetBinding
import me.ndkshr.subroutine.modal.HabitViewItem
import me.ndkshr.subroutine.notification.AlarmReceiver
import me.ndkshr.subroutine.notification.HABIT_ITEM
import me.ndkshr.subroutine.viewmodel.MainActivityViewModel
import me.ndkshr.subroutine.viewmodel.MainActivityViewModelFactory


class HabitMenuBottomSheet(private val habit: HabitViewItem) : BottomSheetDialogFragment() {

    private lateinit var binding: HabitMenuBottomSheetBinding
    private val viewModel: MainActivityViewModel by viewModels(ownerProducer = { requireActivity() }) { MainActivityViewModelFactory() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.habit_menu_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editBtn.setOnClickListener {
            handleEdit()
        }

        binding.cancelReminder.setOnClickListener {
            cancelReminder()
        }

        binding.deleteBtn.setOnClickListener {
            handleDelete()
        }
    }

    private fun handleEdit() {
        // show edit view
        dismiss()
    }

    private fun cancelReminder() {
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra(HABIT_ITEM, habit.habitData)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            habit.habitData.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        dismiss()
    }

    private fun handleDelete() {
        val alertDialog = AlertDialog.Builder(requireActivity())
            .setTitle("Are you sure you want to delete this habit?")
            .setPositiveButton("Yes") { dialog, which ->
                viewModel.delete(habit.habitData)
                this.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }.create()

        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.vintage_beige, null))
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.delete_red, null))
        }

        alertDialog.show()
    }
}