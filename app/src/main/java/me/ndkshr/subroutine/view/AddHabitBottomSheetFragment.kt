package me.ndkshr.subroutine.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.ndkshr.subroutine.R
import me.ndkshr.subroutine.databinding.FragmentAddHabitBottomSheetBinding
import me.ndkshr.subroutine.modal.HabitLabels

class AddHabitBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddHabitBottomSheetBinding

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
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                dialog.behavior.peekHeight = sheet.height
                sheet.parent.parent.requestLayout()
            }
        }

        binding.labelsRv.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        binding.labelsRv.adapter = LabelsAdapter().apply {
            colors = HabitLabels.entries.toList()
        }

        binding.close.setOnClickListener {
            dismiss()
        }

        binding.timePickerSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.reminderTimePicker.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
    }
}