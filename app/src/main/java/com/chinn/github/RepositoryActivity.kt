package com.chinn.github

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class RepositoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)

        val repoName = intent.getStringExtra(REPO_NAME)
        val repoDescription = intent.getStringExtra(REPO_DESC)
        val repoStar = intent.getStringExtra(REPO_STAR)

        findViewById<TextView>(R.id.repoName).apply {
            text = repoName
        }

        findViewById<TextView>(R.id.repoDes).apply {
            text = repoDescription
        }

        findViewById<TextView>(R.id.repoStar).apply {
            text = repoStar
        }
    }
}
