package com.benlefevre.customviewcodelab.findme

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.benlefevre.customviewcodelab.R
import kotlin.math.floor
import kotlin.random.Random

class SpotLightImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var paint = Paint()
    private var shouldDrawSpotLight = false
    private var gameOver = false

    private lateinit var winnerRect: RectF
    private var androidBitmapX = 0f
    private var androidBitmapY = 0f

    private val bitmapAndroid = BitmapFactory.decodeResource(resources, R.drawable.android)
    private val spotLight = BitmapFactory.decodeResource(resources, R.drawable.mask)

    private val shader: Shader
    private val shaderMatrix = Matrix()

    init {
        val bitmap = Bitmap.createBitmap(spotLight.width, spotLight.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val shaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        shaderPaint.color = Color.BLACK
        canvas.drawRect(
            0.0f,
            0.0f,
            spotLight.width.toFloat(),
            spotLight.height.toFloat(),
            shaderPaint
        )

        shaderPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        canvas.drawBitmap(spotLight, 0.0f, 0.0f, shaderPaint)

//        draw only once
        shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
//        repeat horizontally
//        shader = BitmapShader(bitmap,Shader.TileMode.REPEAT, Shader.TileMode.CLAMP)
//        repeat vertically
//        shader = BitmapShader(bitmap,Shader.TileMode.CLAMP, Shader.TileMode.REPEAT)
//        repeat horizontally and vertically
//        shader = BitmapShader(bitmap,Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        paint.shader = shader
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setupWinnerRect()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        Test to see the texture drawn
//        canvas.drawColor(Color.YELLOW)
//        shaderMatrix.setTranslate(100f, 500f)
//        shader.setLocalMatrix(shaderMatrix)
//        canvas.drawRect(0.0f, 0.0f, spotLight.width.toFloat(), spotLight.height.toFloat(), paint)
//        canvas.drawRect(0.0f,0.0f, width.toFloat(),height.toFloat(),paint)

//        Draw the back Android
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(bitmapAndroid, androidBitmapX, androidBitmapY, paint)


        if(!gameOver){
            if (shouldDrawSpotLight){
                canvas.drawRect(0.0f, 0.0f, width.toFloat(), height.toFloat(), paint)
            }else{
                canvas.drawColor(Color.BLACK)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val motionEventX = event.x
        val motionEventY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                shouldDrawSpotLight = true
                if (gameOver) {
                    gameOver = false
                    setupWinnerRect()
                }
            }
            MotionEvent.ACTION_UP -> {
                shouldDrawSpotLight = false
                gameOver = winnerRect.contains(motionEventX,motionEventY)
            }
        }
        shaderMatrix.setTranslate(
            motionEventX - spotLight.width / 2.0f,
            motionEventY - spotLight.height / 2.0f
        )
        shader.setLocalMatrix(shaderMatrix)
        invalidate()
        return true
    }

    private fun setupWinnerRect() {
        androidBitmapX = floor(Random.nextFloat() * (width - bitmapAndroid.width))
        androidBitmapY = floor(Random.nextFloat() * (height - bitmapAndroid.height))

        winnerRect = RectF(
            androidBitmapX,
            androidBitmapY,
            androidBitmapX + bitmapAndroid.width,
            androidBitmapY + bitmapAndroid.height
        )
    }
}