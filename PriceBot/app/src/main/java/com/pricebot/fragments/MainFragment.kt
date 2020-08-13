package com.pricebot.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pricebot.R
import com.pricebot.models.Search
import kotlinx.android.synthetic.main.activity_main.view.*

class MainFragment : Fragment(), View.OnClickListener {
    lateinit var mEtSearchTitle: EditText
    lateinit var mNavController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_search).setOnClickListener(this)
        mEtSearchTitle = view.findViewById(R.id.et_search_title)
        mNavController = Navigation.findNavController(view)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_search->{
                searchBtnClicked()
            }
        }
    }

    private fun searchBtnClicked() {
        var searchTitle = mEtSearchTitle.text.toString()
        searchTitle = searchTitle.trim().replace(" ", "+", true)

        if(!TextUtils.isEmpty(searchTitle)){
            val search = Search(searchTitle)
            val bundle = bundleOf(
                "search" to search
            )
            mNavController.navigate(R.id.action_mainFragment_to_searchResultsFragment, bundle)
        }
        else{
            mEtSearchTitle.error = getString(R.string.blank_search_title)
        }
    }

}