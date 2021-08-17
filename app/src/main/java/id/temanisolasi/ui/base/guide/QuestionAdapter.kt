package id.temanisolasi.ui.base.guide

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.OriginalSize
import coil.transform.RoundedCornersTransformation
import id.temanisolasi.data.model.Question
import id.temanisolasi.databinding.ItemQuestionBinding
import id.temanisolasi.utils.Helpers.formatHtml
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
            when (question.type) {
                0 -> tvAnswer.text = question.answer?.formatHtml()
                1 -> ivAnswer.load(question.answer) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(16f))
                    size(OriginalSize)
                }
            }

            var isExpanded = question.isExpanded ?: false

            contentQuestion.setOnClickListener {
                if (isExpanded) listOf(tvAnswer, ivAnswer)[question.type ?: 0].hideView()
                else listOf(tvAnswer, ivAnswer)[question.type ?: 0].showView()
                isExpanded = !isExpanded
                question.isExpanded = isExpanded
                ivDropDown.animate().setDuration(200).rotationBy(180f).start()
            }
        }
    }

    override fun getItemCount(): Int = listQuestion.size
}