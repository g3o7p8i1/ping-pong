package com.example.pong
import android.animation.ValueAnimator
import android.view.View
import android.util.AttributeSet
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.util.Log
import android.view.MotionEvent



class DrawView  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {



    private var animator: ValueAnimator? = null
    private var x = (((0..1000).random()) / 10) * 10
    private var y = 200
    private var speedy = 5
    private var speedx = 3
    private var start = 0
    private var score = 0
    private var sliderX = 450
    private var sliderSpeed =0
    var go:Int =3
    var mediaPlayer: MediaPlayer? = MediaPlayer.create(context,R.raw.dropsfx)

    private fun saveData()
    {
        val sharedPreferances:SharedPreferences = context?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)?: return
        with(sharedPreferances.edit()){
            val highScore = sharedPreferances.getInt("HighScore",0)
            if(score>highScore)
            {putInt("HighScore",score)
                Log.v("123","yes")
            }
            apply()
        }


    }

    init {

        startAnimate()
        start = 0
        if ((0..1).random() < 0.5)
            speedx *= -1

    }



    private fun startAnimate() {

        animator?.cancel()
        animator = ValueAnimator.ofInt(0, 10).apply {
            repeatCount = -1



            addUpdateListener {



                if(y<2090)
                    y+=speedy

                if(y==2090)
                {
                    if(x>=sliderX && x<=(sliderX+200))
                    {   start=1

                        if(score==2)
                            speedy=7
                        if(score==4)
                            speedy=9
                        if(score==8)
                        {speedy=15
                        speedx=5}
                        if(score==12)
                           speedy=21
                        speedy*=-1
                        mediaPlayer?.start()
                        y+=speedy
                        score++
                    }

                    else {
                        speedy = 0
                        speedx=0
                        go=5
                        saveData()
                        animator?.cancel()
                    }

                }

                if(y==200 && start==1)
                {
                    speedy*=-1
                    y+=speedy
                }

                if (x < width)
                    x += speedx

                if (x > width - 9 || (x in 1..8)) {
                    speedx *= -1
                    x += speedx
                }

                sliderX += sliderSpeed
                if (sliderX <= 30 || sliderX >= (width - 200))
                    sliderSpeed = 0

                invalidate()
            }
        }
        animator?.start()
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        val paint: Paint = Paint()
        paint.color = Color.BLACK
        paint.strokeWidth = 8f

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        paint.color = Color.WHITE

        if(go==3)
        {
        canvas?.drawLine(0f, 178f, width.toFloat(), 178f, paint)
        canvas?.drawCircle(x.toFloat(), y.toFloat(), 20f, paint)
        canvas?.drawRect(sliderX.toFloat(), 2110f, sliderX.toFloat()+200, 2130f, paint)
        paint.textSize = 100f
        canvas?.drawText(("Score: $score"), 350f, 150f, paint)}

        else
        {
            paint.textSize = 100f
            canvas?.drawText(("Game Over"), 300f, 950f, paint)
        }


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x: Float? = event?.x
        val y: Float? = event?.y

        when(event?.action){
        MotionEvent.ACTION_DOWN -> {
            if (y != null) {
                if (y > 1900 && y < 2200)
                    if (x != null) {
                        if (x > 500)
                            if (sliderX < width - 220)
                                sliderSpeed =10

                        if (x < 500)
                            if (sliderX > 30)
                                sliderSpeed= -10

                    }
            }
        }

            MotionEvent.ACTION_UP -> {
                sliderSpeed=0
                return super.onTouchEvent(event)

            }

        }
        return !super.onTouchEvent(event)
    }



}



