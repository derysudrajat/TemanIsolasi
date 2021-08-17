package id.temanisolasi.ui.base.guide

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.temanisolasi.data.model.Question
import id.temanisolasi.databinding.ItemQuestionBinding
import id.temanisolasi.utils.Helpers.hideView
import id.temanisolasi.utils.Helpers.showView

class QuestionAdapter(
    private val context: Context,
    private val listQuestion: List<Question>
) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: ItemQuestionBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemQuestionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = listQuestion[position]
        with(binding) {
            tvQuestion.text = question.question
            tvAnswer.text = question.answer

            var isExpanded = question.isExpanded ?: false

            contentQuestion.setOnClickListener {
                if (isExpanded) tvAnswer.hideView() else tvAnswer.showView()
                isExpanded = !isExpanded
                question.isExpanded = isExpanded
                ivDropDown.animate().setDuration(200).rotationBy(180f).start()
            }
        }
    }

    override fun getItemCount(): Int = listQuestion.size
}