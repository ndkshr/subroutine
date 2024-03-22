package me.ndkshr.subroutine.view

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
            // show edit view
            dismiss()
        }

        binding.deleteBtn.setOnClickListener {
            viewModel.delete(habit.habitData)
            dismiss()
        }
    }
}