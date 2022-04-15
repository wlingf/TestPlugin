package cc.jianke.testplugin.wanandroid.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cc.jianke.testplugin.R

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 11:15
 * @Description:
 */
class TitleBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    private var title: String = ""
    private var endText: String = ""

    private lateinit var mOnClickListener: OnTitleBarViewListener

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView)
        title = ta.getString(R.styleable.TitleBarView_tbv_title).toString()
        endText = ta.getString(R.styleable.TitleBarView_tbv_end_text).toString()
        ta.recycle()
        initView()
    }

    private fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.view_title_bar, this, true)
//        addView(view, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))

        if (!title.isNullOrEmpty() && "null" != title){
            findViewById<TextView>(R.id.tv_title).text = title
        }
        if (!endText.isNullOrEmpty() && "null" != endText){
            findViewById<TextView>(R.id.tv_end_first_title).text = endText
            findViewById<TextView>(R.id.tv_end_first_title).setOnClickListener {
                mOnClickListener?.onEndTextClick()
            }
        }

        findViewById<ImageView>(R.id.iv_back).setOnClickListener {
            if (context is Activity) {
                (context as Activity).finish()
            }
        }
    }

    fun setOnClickListener(onClickListener: OnTitleBarViewListener) {
        this.mOnClickListener = onClickListener
    }

    fun setVisibleEndText(isVisible: Boolean) {
        findViewById<TextView>(R.id.tv_end_first_title).visibility = if (isVisible) VISIBLE else GONE
    }

    fun setTitle(title: String) {
        if (!title.isNullOrEmpty()){
            findViewById<TextView>(R.id.tv_title).text = title
        }
    }

    interface OnTitleBarViewListener {

        fun onEndTextClick()
    }
}
