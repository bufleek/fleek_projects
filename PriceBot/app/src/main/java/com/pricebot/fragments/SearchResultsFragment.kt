package com.pricebot.fragments

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.pricebot.R
import com.pricebot.models.Search
import com.pricebot.models.SearchResult
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.lang.Exception
import java.net.URI
import java.net.URL

class SearchResultsFragment : Fragment(){
    var searchResults: ArrayList<SearchResult> = ArrayList()
    private lateinit var search: Search
    lateinit var tvFoundNothing: TextView
    lateinit var progressbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            if (it != null) {
                search = it.getParcelable<Search>("search")!!

                val jumiaSearchUrl = URI.create("https://www.jumia.co.ke/catalog/?q=${search.title}").toURL()
                val kilimallSearchUrl = URI.create("https://www.kilimall.co.ke/new/commoditysearch?q=${search.title}").toURL()

                searchResults.clear()
                //JumiaBackgroundFetch().execute(jumiaSearchUrl)
                KilimallBackgroundFetch().execute(kilimallSearchUrl)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvFoundNothing = view.findViewById(R.id.tv_found_nothing)
        progressbar = view.findViewById(R.id.progressBar_search_results)
        Toast.makeText(activity, search.title, Toast.LENGTH_SHORT).show()
    }


    inner class JumiaBackgroundFetch: AsyncTask<URL, Void, Int>(){
        private val TAG = "SearchResultsFragment"

        override fun doInBackground(vararg p0: URL?): Int {
            try{
                val jumiasearchUrl = p0[0].toString()
                val jumiaDoc :Document = Jsoup.connect(jumiasearchUrl).followRedirects(true).get()
                val jumiaProducts: Elements = jumiaDoc.select(getString(R.string.jumia_product_key))
                for(jumiaProduct: Element in jumiaProducts ){
                    var productName = jumiaProduct.select(getString(R.string.jumia_product_name_key)).text()
                    var productPrice = jumiaProduct.select(getString(R.string.jumia_product_price_key)).text()
                    var productLink = jumiaProduct.select(getString(R.string.jumia_product_link_key)).attr("abs:href")
                    //var imageLink = jumiaProduct.select(getString(R.string.jumia_product_image_key)).attr("src")
                    var oldPrice = ""
                    try{
                        oldPrice = jumiaProduct.select(getString(R.string.jumia_product_initialPrice_key)).text()
                    }catch (e: Exception){
                        oldPrice = "NULL"
                        e.printStackTrace()
                    }
                    var percentageDiscount = ""
                    try{
                        percentageDiscount = jumiaProduct.select(getString(R.string.jumia_percentage_discount_key)).text()
                    }catch (e: Exception){
                        percentageDiscount = "NULL"
                        e.printStackTrace()
                    }
                    val searchResult = SearchResult("Jumia", productName, productPrice, oldPrice, percentageDiscount, productLink)
                    searchResults.add(searchResult)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }

            return searchResults.size
        }

        override fun onPostExecute(result: Int) {
            //if (result < 1){
                tvFoundNothing.visibility = View.VISIBLE
            //}
            tvFoundNothing.text = result.toString()
            progressbar.visibility = View.INVISIBLE
        }
    }

    inner class KilimallBackgroundFetch: AsyncTask<URL, Void, String>() {
        override fun doInBackground(vararg p0: URL?): String {
            val kiliMallSearchUrl = p0[0].toString()
            val kilimallDoc: Document = Jsoup.connect(kiliMallSearchUrl).followRedirects(false).get()
            val products: Elements = kilimallDoc.select(getString(R.string.kilimall_products_key))

            var message = ""
            for (product: Element in products){
                //val productName = product.select(getString(R.string.kilimall_product_name_key))
                val productLink = product.select(getString(R.string.kilimall_product_link_key)).attr("abs:href")
                message = "Url: $productLink"
            }

            return message
        }

        override fun onPostExecute(result: String?) {
            tvFoundNothing.visibility = View.VISIBLE
            tvFoundNothing.text = result
        }
    }


}