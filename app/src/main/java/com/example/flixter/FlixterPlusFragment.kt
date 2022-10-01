package com.example.flixter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONObject


private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class FlixterPlusFragment {

    /*
        * Constructing the view
        */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flixter_plus_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.filmList) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY

// Using the client, perform the HTTP request
        client[
                "https://api.themoviedb.org/3/movie/now_playing",
                params,
                object : JsonHttpResponseHandler()
                { //connect these callbacks to your API call

                    override fun onSuccess(

                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON

                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        //TODO - Parse JSON into Models

                        val resultsJSON : JSONObject = json.jsonObject.get("results") as JSONObject
                        val filmsRawJSON : String = resultsJSON.get("movies").toString()
                        val gson = Gson()
                        val arrayFilmType = object : TypeToken<List<FlixterPlus>>() {}.type
                        val models : List<FlixterPlus> = gson.fromJson(filmsRawJSON, arrayFilmType)
                        recyclerView.adapter = FlixterPlusRecyclerViewAdapter(models, this@FlixterPlusFragment)


                        // Look for this in Logcat:
                        Log.d("FlixterPlusFragment", "response successful")
                    }

                    /*
                     * The onFailure function gets called when
                     * HTTP response status is "4XX" (eg. 401, 403, 404)
                     */
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        // If the error is not null, log it!
                        t?.message?.let {
                            Log.e("FlixterPlusFragment", errorResponse)
                        }
                    }
                }]


    }

    /*
     * What happens when a particular book is clicked.
     */
    override fun onItemClick(item: FlixterPlus) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}