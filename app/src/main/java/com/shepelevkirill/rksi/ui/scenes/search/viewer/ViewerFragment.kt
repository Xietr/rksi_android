package com.shepelevkirill.rksi.ui.scenes.search.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.shepelevkirill.rksi.MvpFragment
import com.shepelevkirill.rksi.R
import com.shepelevkirill.rksi.data.core.enums.SearchType

class ViewerFragment : MvpFragment(), ViewerMvpView {
    @InjectPresenter lateinit var presenter: ViewerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity!!.title = arguments?.getString("SearchFor")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_viewer, container, false)
    }

    companion object {
        fun newInstance(searchType: SearchType, searchFor: String): ViewerFragment {
            val bundle = Bundle()

            bundle.putSerializable("SearchType", searchType)
            bundle.putString("SearchFor", searchFor)

            val fragment = ViewerFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}