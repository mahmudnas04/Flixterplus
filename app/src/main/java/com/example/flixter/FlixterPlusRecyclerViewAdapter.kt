package com.example.flixter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixter.R.id


class FlixterPlusRecyclerViewAdapter(
    private val movies: List<FlixterPlus>,
    private val mListener: OnListFragmentInteractionListener?
) {
    : RecyclerView.Adapter<FlixterPlusRecyclerViewAdapter.FilmsViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_flixter_plus, parent, false)
            return FilmsViewHolder(view)
        }

        /**
         * This inner class lets us refer to all the different View elements
         * (Yes, the same ones as in the XML layout files!)
         */
        inner class FilmsViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            var mItem: FlixterPlus? = null
            val mfilmTitle: TextView = mView.findViewById<View>(id.film_title) as TextView
            val mfilmDescription: TextView = mView.findViewById<View>(id.film_description) as TextView
            val mfilmImage: ImageView = mView.findViewById<View>(id.imageView) as ImageView
        }

        /**
         * This lets us "bind" each Views in the ViewHolder to its' actual data!
         */
        override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
            val movie = movies[position]
            val url = "https://image.tmdb.org/t/p/w500/"

            holder.mItem = movie
            holder.mfilmTitle.text = movie.title
            holder.mfilmDescription.text = movie.description
            movie.filmImageUrl = url + movie.filmImageUrl


            Glide.with(holder.mView)
                .load(movie.filmImageUrl)
                .centerInside()
                .into(holder.mfilmImage)

            holder.mView.setOnClickListener {
                holder.mItem?.let { url ->
                    mListener?.onItemClick(url)
                }
            }
        }

        /**
         * Remember: RecyclerView adapters require a getItemCount() method.
         */
        override fun getItemCount(): Int {
            return movies.size
        }
    }


}