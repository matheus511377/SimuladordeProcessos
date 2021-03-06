package com.matheus.simuladordeprocessos.simuladordeprocessos.Classes;

/**
 * Created by Matheus on 13/09/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.matheus.simuladordeprocessos.R;

import java.util.HashMap;

import java.util.List;

public class adapterListViewEspancivel extends BaseExpandableListAdapter {
    private List<Processo> ListaProcessos;
    private Context context;
    private LayoutInflater inflater;
    private List<String> lstGrupos;
    private HashMap<String, List<Fila>> lstItensGrupos;

    private static class ViewHolder {
        public TextView txt3;
        public TextView txtPID;
        public TextView txtT;
        public TextView txtTE;

        private ViewHolder() {
        }
    }

    public adapterListViewEspancivel(Context context, List<String> grupos, HashMap<String, List<Fila>> itensGrupos, List<Processo> processos) {
        this.context = context;
        this.lstGrupos = grupos;
        this.lstItensGrupos = itensGrupos;
        this.inflater = LayoutInflater.from(context);
        this.ListaProcessos = processos;
    }

    public int getGroupCount() {
        return this.lstGrupos.size();
    }

    public int getChildrenCount(int groupPosition) {
        return (lstItensGrupos.get(getGroup(groupPosition))).size();
    }

    public Object getGroup(int groupPosition) {
        return this.lstGrupos.get(groupPosition);
    }

    public Object getChild(int groupPosition, int childPosition) {
        return ((List) this.lstItensGrupos.get(getGroup(groupPosition))).get(childPosition);
    }

    public long getGroupId(int groupPosition) {
        return (long) groupPosition;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return (long) childPosition;
    }

    public boolean hasStableIds() {
        return false;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pai_adapter, null);
        }
        TextView tvQtde = (TextView) convertView.findViewById(R.id.tvQtde);
        ((TextView) convertView.findViewById(R.id.tvGrupo)).setText((String) getGroup(groupPosition));
        tvQtde.setText(String.valueOf(getChildrenCount(groupPosition)));
        return convertView;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder vh;
        View view = convertView;
        if (view == null) {
            view =inflater.inflate(R.layout.adapter_processos, parent, false);
            vh = new ViewHolder();
            vh.txtPID = (TextView) view.findViewById(R.id.txtID);
            vh.txtTE = (TextView) view.findViewById(R.id.txtTE);
            vh.txtT = (TextView) view.findViewById(R.id.txtT);
            vh.txt3 = (TextView) view.findViewById(R.id.txt3);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        Fila fila = (Fila) getChild(groupPosition, childPosition);
        vh.txtPID.setText(String.valueOf(fila.getId()));
        if ((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getEstado() == "BLOQUEADO") {
            vh.txtTE.setText(String.valueOf((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getTempoExecutandoTotalES()));
            vh.txtT.setText(String.valueOf((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getTempoTotalES()));
        }
        if ((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getEstado() == "APTO") {
            vh.txtTE.setText(String.valueOf((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getTempoExecutando()));
            vh.txtT.setText(String.valueOf((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getTempoTotal()));
        }
        if ((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getEstado() == "DESTRUIDO") {
            vh.txtTE.setText(String.valueOf((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getTempoTotal()));
            vh.txtT.setText(String.valueOf((ListaProcessos.get(buscaPosicaoListaProcessos(fila.getId()))).getTempoTotal()));
        }
        return view;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private int buscaPosicaoListaProcessos(int id) {
        for (int i = 0; i <= ListaProcessos.size(); i++) {
            if ((ListaProcessos.get(i)).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
