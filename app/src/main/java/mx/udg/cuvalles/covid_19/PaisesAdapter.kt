package mx.udg.cuvalles.covid_19

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PaisesAdapter(paises:ArrayList<Pais>, contexto:Context):RecyclerView.Adapter<PaisesAdapter.ViewHolder>() {
    var listaPaises:ArrayList<Pais>?=null
    var contexto:Context?=null

    init {
        this.listaPaises = paises
        this.contexto = contexto
    }

    class ViewHolder(vista: View):RecyclerView.ViewHolder(vista){
        var nombrePais:TextView?=null
        var numConfirmados:TextView?=null
        var numMuertos:TextView?=null
        var numRecuperados:TextView?=null
        var bandera:ImageView?=null


        init {
            nombrePais = vista.findViewById(R.id.tvNombrePais)
            numConfirmados = vista.findViewById(R.id.tvConfirmados)
            numMuertos = vista.findViewById(R.id.tvMuertos)
            numRecuperados = vista.findViewById(R.id.tvRecuperados)
            bandera = vista.findViewById(R.id.ivBandera)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vistaPais:View = LayoutInflater.from(contexto).inflate(R.layout.pais_item,parent, false)
        val paisViewHolder = ViewHolder(vistaPais)
        vistaPais.tag = paisViewHolder
        return paisViewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombrePais!!.text = listaPaises!![position].nombre
        holder.numConfirmados!!.text = "${listaPaises!![position].confirmados}"
        holder.numMuertos!!.text = "${listaPaises!![position].muertos}"
        holder.numRecuperados!!.text = "${listaPaises!![position].recuperados}"
        Picasso.get().load("https://www.countryflags.io/${listaPaises!![position].codPais}/flat/64.png").into(holder.bandera)
    }

    override fun getItemCount(): Int {
        return listaPaises!!.count()
    }

}