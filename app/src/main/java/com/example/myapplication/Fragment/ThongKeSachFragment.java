package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.myapplication.Adapter.TopAdapter;
import com.example.myapplication.DAO.ThongkeDAO;import myapplication.R;
import com.example.myapplication.model.Top;

public class ThongKeSachFragment extends Fragment {
    private RecyclerView recyclerView;
    private ThongkeDAO daoThongKe;
    private TopAdapter adapter;
    private ArrayList<Top> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thong_ke_sach, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        daoThongKe = new ThongkeDAO(getContext());
        arrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rcvSach);

        showData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void showData() {
        arrayList.clear();
        arrayList = (ArrayList<Top>) daoThongKe.getTop();
        adapter = new TopAdapter(getContext());
        adapter.setData(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}
