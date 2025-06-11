package com.example.hospital_managment.UserDashboard.Chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospital_managment.GetIdFromToken;
import com.example.hospital_managment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatView.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatView newInstance(String param1, String param2) {
        ChatView fragment = new ChatView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ChatViewModel chatViewModel;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_chat_view2, container, false);

        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerViewChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatAdapter = new ChatAdapter(new ArrayList<>(),requireContext());

        recyclerView.setAdapter(chatAdapter);

        chatViewModel.getChats().observe(getViewLifecycleOwner(), existingChats -> {
            chatViewModel.getAllDoctors().observe(getViewLifecycleOwner(), allDoctors -> {
                List<ChatModel> combinedChats = new ArrayList<>();
                GetIdFromToken getIdFromToken = new GetIdFromToken();
                String patientId = String.valueOf(getIdFromToken.getId(requireContext()));


                combinedChats.addAll(existingChats);

                for (GetDoctorsDTO doctor : allDoctors) {
                    String doctorId = String.valueOf(doctor.getId());

                    boolean alreadyChatted = false;
                    for (ChatModel chat : existingChats) {
                        if ((chat.getUserId1().equals(patientId) && chat.getUserId2().equals(doctorId)) ||
                                (chat.getUserId2().equals(patientId) && chat.getUserId1().equals(doctorId))) {
                            alreadyChatted = true;
                            break;
                        }
                    }

                    if (!alreadyChatted) {
                        ChatModel starterChat = new ChatModel();
                        starterChat.setUserId1(patientId);
                        starterChat.setUserId2(doctorId);
                        combinedChats.add(starterChat);
                    }
                }

                chatAdapter.updateList(combinedChats);
            });
            chatViewModel.fetchAllDoctors(requireContext());
        });

        chatViewModel.fetchChats(requireContext());

       return view;
    }
}