package mx.udg.cuvalles.covid_19

import android.app.VoiceInteractor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import mx.udg.cuvalles.covid_19.BuildConfig.DEBUG
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    var miRecycler:RecyclerView?=null
    var adaptadorPaises:PaisesAdapter?=null
    var listaPaises:ArrayList<Pais>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        listaPaises = ArrayList<Pais>()
        adaptadorPaises = PaisesAdapter(listaPaises!!, this)

        //listaPaises.add(Pais("Mexico", 23, 5, 1))

        miRecycler =  findViewById(R.id.miRecycler)

        miRecycler!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        miRecycler!!.adapter = adaptadorPaises

        val queue = Volley.newRequestQueue(this)
        val url = "https://wuhan-coronavirus-api.laeyoung.endpoint.ainize.ai/jhu-edu/latest"

        val peticionDatos = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener { response ->
            for (index in 0..response.length()-1) {
                try {
                    val paisJason = response.getJSONObject(index)
                    val nombrePais = paisJason.getString("countryregion")
                    val numConfirmados = paisJason.getInt("confirmed")
                    val numMuertos = paisJason.getInt("deaths")
                    val numRecuperados = paisJason.getInt("recovered")
                    val countryCodeJson =  paisJason.getJSONObject("countrycode")
                    val codigoPais = countryCodeJson.getString("iso2")
                    val paisIndividual = Pais(nombrePais, numConfirmados, numMuertos, numRecuperados, codigoPais)
                    listaPaises!!.add(paisIndividual)

                } catch (e: JSONException) {
                    Log.wtf("JSonError", e.localizedMessage)
                }
            }
            listaPaises!!.sortBy { it.confirmados }
            adaptadorPaises!!.notifyDataSetChanged()
        },
        Response.ErrorListener { error ->
            Log.e("error_volley", error.localizedMessage)

        })

        queue.add(peticionDatos)


    }
}