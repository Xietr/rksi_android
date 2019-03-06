package com.shepelevkirill.rksi.ui.scenes.intro

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpActivity
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPagerBuilder
import com.shepelevkirill.rksi.R
import com.shepelevkirill.rksi.ui.scenes.MainActivity

class IntroActivity : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val slide = SliderPagerBuilder().title("Привет").description("Не хочется ли тебе немного упростить себе жизнь?").build()
        val slide2 = SliderPagerBuilder().title("Introduction").description("Это приложение позволяет тебе смотреть актуальное расписание РКСИ. Это удобно, потому что тебе не нужно каждый раз заходить на сайт мобильного колледжа.").build()
        val slide3 = SliderPagerBuilder().title("Features").description("Кроме того, приложение расчитывает время до конца/начала пары и тебе не нужно считать это в голове :)").build()

        addSlide(AppIntroFragment.newInstance(slide))
        addSlide(AppIntroFragment.newInstance(slide2))
        addSlide(AppIntroFragment.newInstance(slide3))
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}