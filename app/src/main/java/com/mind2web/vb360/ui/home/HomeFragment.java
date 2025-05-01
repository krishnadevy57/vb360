package com.mind2web.vb360.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;
import com.mind2web.vb360.R;
import com.mind2web.vb360.VoiceActivity;
import com.mind2web.vb360.adapters.SuggestedUserAdapter;
import com.mind2web.vb360.apiservice.ApiService;
import com.mind2web.vb360.modeles.PhoneNumberSuggestedData;
import com.mind2web.vb360.modeles.PhoneNumberSuggestionsResponse;
import com.mind2web.vb360.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {

//    private TextView phoneNumberDisplay;
    private EditText svSearchUser;
    private LinearLayout llSelectedContactView;
    CountryCodePicker countryCodePicker;
//    private StringBuilder phoneNumber = new StringBuilder();
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable apiCallRunnable;

    private TextView tv_selected_name,tv_selected_phone,tv_selected_company;
    private RecyclerView recyclerView;
    private SuggestedUserAdapter adapter;
    private List<PhoneNumberSuggestedData> suggestedUserList = new ArrayList<>();
boolean isSelctedContact = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

//        phoneNumberDisplay = rootView.findViewById(R.id.phone_number_display);
        svSearchUser = rootView.findViewById(R.id.sv_search_user);

        countryCodePicker = rootView.findViewById(R.id.countryCodePicker);
        llSelectedContactView = rootView.findViewById(R.id.ll_selected_contact_view);

        tv_selected_name = rootView.findViewById(R.id.tv_selected_name);
        tv_selected_phone = rootView.findViewById(R.id.tv_selected_phone);
        tv_selected_company = rootView.findViewById(R.id.tv_selected_company);
//        tv_selected_email = rootView.findViewById(R.id.tv_selected_email);
//        tv_selected_address = rootView.findViewById(R.id.tv_selected_address);


        llSelectedContactView.setVisibility(View.GONE);
//        LinearLayout dialPad = rootView.findViewById(R.id.dial_pad);
//        TextView dialPad1ButtonText = rootView.findViewById(R.id.dial_pad_1button_text);
//        TextView dialPad2ButtonText = rootView.findViewById(R.id.dial_pad_2button_text);
//        TextView dialPad3ButtonText = rootView.findViewById(R.id.dial_pad_3button_text);
//        TextView dialPad4ButtonText = rootView.findViewById(R.id.dial_pad_4button_text);
//        TextView dialPad5ButtonText = rootView.findViewById(R.id.dial_pad_5button_text);
//        TextView dialPad6ButtonText = rootView.findViewById(R.id.dial_pad_6button_text);
//        TextView dialPad7ButtonText = rootView.findViewById(R.id.dial_pad_7button_text);
//        TextView dialPad8ButtonText = rootView.findViewById(R.id.dial_pad_8button_text);
//        TextView dialPad9ButtonText = rootView.findViewById(R.id.dial_pad_9button_text);
//        TextView dialPad10ButtonText = rootView.findViewById(R.id.dial_pad_10button_text);
//        TextView dialPad11ButtonText = rootView.findViewById(R.id.dial_pad_11button_text);
//        TextView dialPad12ButtonText = rootView.findViewById(R.id.dial_pad_12button_text);
        recyclerView = rootView.findViewById(R.id.suggested_user_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SuggestedUserAdapter(suggestedUserList,(item, position) -> {
            // Handle the click event here
            isSelctedContact = true;
            detectCountryFromPhoneNumber(item.getPhone());

            tv_selected_name.setText(item.getCustomUserName());
            tv_selected_phone.setText(item.getPhone());
            tv_selected_company.setText(item.getLabel());
//            tv_selected_email.setText(item.getEmail());
//            tv_selected_address.setText(item.getLabel());

            llSelectedContactView.setVisibility(View.VISIBLE);
            suggestedUserList.clear();

            adapter.notifyDataSetChanged();
        });


        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        svSearchUser.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Close the keyboard
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(svSearchUser.getWindowToken(), 0);
                }
                return true;
            }
            return false;
        });

        svSearchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (apiCallRunnable != null) {
                    handler.removeCallbacks(apiCallRunnable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isSelctedContact)
                {
                    isSelctedContact= false;
                }else{
                    apiCallRunnable = () -> callApi(svSearchUser.getText().toString());
                    handler.postDelayed(apiCallRunnable, 300);
                }

            }
        });
        // Call button
        ImageButton callButton = rootView.findViewById(R.id.call_button);
        callButton.setOnClickListener(v -> {

            if(isValidMobileNumber(svSearchUser.getText().toString())){
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    ApiService apiService = new ApiService();
                    apiService.updateCallStatus();
                });
            Intent intent = new Intent(getActivity(), VoiceActivity.class);
            intent.putExtra("phone",countryCodePicker.getSelectedCountryCode()+svSearchUser.getText().toString());
            startActivity(intent);
//            Toast.makeText(getActivity(), "Calling: " + svSearchUser.getText(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
            }

        });

        // Backspace button
//        ImageButton backspaceButton = rootView.findViewById(R.id.backspace_button);

        if (getArguments() != null) {
            String phone = getArguments().getString("phone", "");
            // Use the data in UI
            tv_selected_phone.setText(phone);
        }
        return rootView;
    }
    public static boolean isValidMobileNumber(String mobileNumber) {
        String regex = "^[+]?[1-9]\\d{1,14}$";
        return Pattern.matches(regex, mobileNumber);
    }
    private void detectCountryFromPhoneNumber(String phone) {
//if(phone.contains("+")){
//
//}else {
//    phone="+"+phone;
//}
        // Initialize PhoneNumberUtil
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            // Parse the phone number
            Phonenumber.PhoneNumber parsedNumber = phoneUtil.parse(phone, null);

            // Extract the country code
            int countryCode = parsedNumber.getCountryCode();
            countryCodePicker.setCountryForPhoneCode(countryCode);
            System.out.println("Country Code: +" + countryCode);

            // Extract the national number
            long nationalNumber = parsedNumber.getNationalNumber();
            System.out.println("Phone Number: " + nationalNumber);
            svSearchUser.setText(""+nationalNumber);

//            updatePhoneNumberDisplay();

        } catch (NumberParseException e) {
            svSearchUser.setText(""+phone);
            System.err.println("NumberParseException: " + e.toString());
//            updatePhoneNumberDisplay();
        }
    }

    private void callApi(String phoneNumber) {
        // Your API call logic here
        Log.d("API_CALL", "API called with phone number: " + phoneNumber);
        suggestedUserList.clear();
        llSelectedContactView.setVisibility(View.GONE);
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            ApiService apiService = new ApiService();
            PhoneNumberSuggestionsResponse phoneNumberSuggestionsResponse = apiService.getPhoneNumbersSuggestions(phoneNumber);
            if(getActivity()!=null){
                getActivity().runOnUiThread(() -> {
                    if(phoneNumberSuggestionsResponse!=null){

                        if(getActivity()!=null){


                            suggestedUserList.clear();
                            if(phoneNumberSuggestionsResponse.getData()!=null){
                                suggestedUserList.addAll(phoneNumberSuggestionsResponse.getData());

                            }else {
                                suggestedUserList.clear();
                            }

                            adapter.notifyDataSetChanged();


                        }
                    }else {
                        suggestedUserList.clear();
                        adapter.notifyDataSetChanged();
                    }
                });
            }

        });
    }

//    private void updatePhoneNumberDisplay() {
//        svSearchUser.setText(phoneNumber.toString());
//    }

    // Interface for click listener
    public interface OnItemClickListener {
        void onItemClick(PhoneNumberSuggestedData item, int position);
    }


}
