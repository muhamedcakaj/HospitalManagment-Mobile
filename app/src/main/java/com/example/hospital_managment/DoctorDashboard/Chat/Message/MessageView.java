package com.example.hospital_managment.DoctorDashboard.Chat.Message;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.hospital_managment.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessageView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageView.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageView newInstance(String param1, String param2) {
        MessageView fragment = new MessageView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    String patientId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            patientId = getArguments().getString("chatId");
        }
    }

    private RecyclerView recyclerView;
    private MessageViewModel messageViewModel;
    private MessageAdapter messageAdapter;

    private ImageView sentMessageIcon;

    private EditText sentMessageEditText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_view, container, false);

        sentMessageIcon = view.findViewById(R.id.sentMessageIcon);
        sentMessageEditText = view.findViewById(R.id.sentMessageEditText);

        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerViewChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messageAdapter = new MessageAdapter(new ArrayList<>(), requireContext());
        recyclerView.setAdapter(messageAdapter);


        messageViewModel.connectWebSocket(requireContext());

        messageViewModel.getMessage().observe(getViewLifecycleOwner(), messages -> {
            messageAdapter.updateList(messages);
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        });

        messageViewModel.fetchMessage(requireContext(), patientId);

        sentMessageIcon.setOnClickListener(v -> {
            String messageText = sentMessageEditText.getText().toString().trim();
            if (!messageText.isEmpty()) {
                messageViewModel.sendMessage(patientId, messageText, requireContext());
                sentMessageEditText.setText("");
            }
        });

        return view;
    }
}