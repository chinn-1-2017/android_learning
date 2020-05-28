package com.chinn.github

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

const val REPO_NAME = "com.chinn.github.REPO_NAME"
const val REPO_DESC = "com.chinn.github.REPO_DESC"
const val REPO_STAR = "com.chinn.github.REPO_STAR"

class MainActivity : AppCompatActivity() {
    var repositories = ArrayList<Repository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch.setOnClickListener {
            val searchText = txtKeyword.text.toString()

            if (searchText.isEmpty()) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            val githubApi: Uri.Builder = Uri.Builder()
            githubApi.scheme("https")
                .authority("api.github.com")
                .appendPath("search")
                .appendPath("repositories")
                .appendQueryParameter("q", searchText)
                .appendQueryParameter("sort", "stars")
                .appendQueryParameter("order", "order")

            GithubTask().execute(githubApi.toString())
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class GithubTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val stringUrl: String = params.first()!!
            val myUrl = URL(stringUrl)
            val connection = myUrl.openConnection() as HttpURLConnection
            return connection.inputStream.bufferedReader().readText()
        }

        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)

            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
            val obj = JSONObject(result)
            val data = obj.optJSONArray("items")

            for (i in 0 until data.length()) {
                val repository = data.getJSONObject(i)
                repositories.add(
                    i, Repository(
                        repository.getString("name"),
                        repository.getString("description"),
                        repository.getInt("stargazers_count")
                    )
                )
            }

            val listView = findViewById<ListView>(R.id.listRepository)

            listView.adapter = GithubAdapter(this@MainActivity, repositories)

            listView.setOnItemClickListener { _, _, position, _ ->
                val repository = repositories[position]

                val intent = Intent(this@MainActivity, RepositoryActivity::class.java)

                intent.putExtra(REPO_NAME, repository.name)
                intent.putExtra(REPO_DESC, repository.description)
                intent.putExtra(REPO_STAR, repository.star)

                startActivity(intent)
            }
        }

    }
}
