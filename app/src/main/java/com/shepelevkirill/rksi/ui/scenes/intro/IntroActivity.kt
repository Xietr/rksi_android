package com.shepelevkirill.rksi.ui.scenes.intro

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage
import com.github.paolorotolo.appintro.model.SliderPagerBuilder
import com.shepelevkirill.rksi.App
import com.shepelevkirill.rksi.data.core.repository.PreferencesRepository
import com.shepelevkirill.rksi.ui.scenes.main.MainActivity
import javax.inject.Inject

class IntroActivity : AppIntro2() {
    @Inject lateinit var preferencesRepository: PreferencesRepository

    init {
        App.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (preferencesRepository.getIsIntroScreenShown()) {
            startApplication()
        } else {
            preferencesRepository.putIsIntroScreenShown(true)
        }

        skipButtonEnabled = false

        val helloSlide = getHelloSlide()
        val featuresSlide = getFeaturesSlide()
        val settingsSlide = getSettingsSlide()

        addSlide(AppIntroFragment.newInstance(helloSlide))
        addSlide(AppIntroFragment.newInstance(featuresSlide))
        addSlide(AppIntroFragment.newInstance(settingsSlide))
    }

    private fun getHelloSlide(): SliderPage {
        return SliderPagerBuilder()
            .title("Привет")
            .description("Не хочется ли тебе немного упростить себе жизнь?")
            .build()
    }

    private fun getFeaturesSlide(): SliderPage {
        return SliderPagerBuilder()
            .title("Как?")
            .description("Это приложение позволяет тебе смотреть актуальное расписание РКСИ. Это удобно, потому что тебе не нужно каждый раз заходить на сайт мобильного колледжа.")
            .build()
    }

    private fun getSettingsSlide(): SliderPage {
        return SliderPagerBuilder()
            .title("Настройки")
            .description("Первым делом загляни в настройки и выбери свою группу!")
            .build()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        startApplication()
    }

    private fun startApplication() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
        startActivity(intent)
        finish()
    }

}