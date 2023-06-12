package com.vikvita.first_advanced_project

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Interpolator
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var textSize= resources.getDimension(R.dimen.default_text_size)
private lateinit var background:Bitmap
private lateinit var extraCanvas:Canvas
private var pie:Float=0f
   private var downloadwidthSize=0f
   private var valueAnimatorForPie =ValueAnimator.ofFloat(0f,360f)
   private lateinit var valueAnimatorForDownload:ValueAnimator

   private var backgroundColor=0
   private var buttonText=""
    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->


    }
    private var paint=Paint().apply {


        isAntiAlias = true
        style = Paint.Style.FILL
        textSize = resources.getDimension(R.dimen.default_text_size)
    }





    init {
       context.withStyledAttributes(attrs,R.styleable.LoadingButton){
           backgroundColor=getColor(R.styleable.LoadingButton_backgroundColo,0)
           buttonText= getString(R.styleable.LoadingButton_textOfButton).toString()

       }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (::background.isInitialized) background.recycle()
        background= Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        extraCanvas= Canvas(background)
        extraCanvas.drawColor(backgroundColor)


    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
         canvas?.drawBitmap(background,0f,0f,null)

        paint.color=Color.WHITE
        canvas?.drawText(buttonText,(widthSize/2.9).toFloat(),((heightSize-textSize)),paint)






        paint.color=resources.getColor(R.color.colorAccent)
        canvas?.drawArc((widthSize/1.5).toFloat(),30f,(widthSize-185).toFloat(),(heightSize-30).toFloat(),0f,pie,true,paint)

        paint.color=resources.getColor(R.color.colorPrimaryDark)
        extraCanvas.drawRect(0f,0f,downloadwidthSize,heightSize.toFloat(),paint)



    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)

    }
fun move(){


    val animaterListent=object: AnimatorListenerAdapter(){
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            downloadwidthSize=0f
            pie=0f
            extraCanvas.drawColor(resources.getColor(R.color.colorPrimary))
            invalidate()
        }



        override fun onAnimationRepeat(animation: Animator) {
            super.onAnimationRepeat(animation)
            downloadwidthSize=0f
            pie=0f
            extraCanvas.drawColor(backgroundColor)
            invalidate()
        }
    }


    valueAnimatorForDownload= ValueAnimator.ofFloat(0f,widthSize.toFloat())
    valueAnimatorForPie.duration = 3000 //in millis
    valueAnimatorForPie.addUpdateListener {

        pie = it.animatedValue as Float
        invalidate()
    }

    valueAnimatorForPie.repeatCount=ValueAnimator.INFINITE
    valueAnimatorForPie.addListener(animaterListent)


    valueAnimatorForDownload.duration = 3000//in millis
    valueAnimatorForDownload.addUpdateListener {

        downloadwidthSize = it.animatedValue as Float
        invalidate()
    }
    valueAnimatorForDownload.repeatCount=ValueAnimator.INFINITE
    valueAnimatorForDownload.addListener(animaterListent)
    valueAnimatorForDownload.start()
    valueAnimatorForPie.start()
}

    fun stopAnimation(){

        valueAnimatorForDownload.end()
        valueAnimatorForPie.end()

    }

    fun stopRepeat(){
        valueAnimatorForDownload.repeatCount=0
        valueAnimatorForPie.repeatCount=0
    }

}