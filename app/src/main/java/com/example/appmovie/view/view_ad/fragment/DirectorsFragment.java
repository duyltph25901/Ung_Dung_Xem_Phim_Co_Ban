package com.example.appmovie.view.view_ad.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmovie.R;
import com.example.appmovie.class_supported.ConvertImageStore;
import com.example.appmovie.db.db_director.DirectorDatabase;
import com.example.appmovie.model.Director;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DirectorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DirectorsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DirectorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DirectorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DirectorsFragment newInstance(String param1, String param2) {
        DirectorsFragment fragment = new DirectorsFragment();
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

    private CircleImageView img;
    private EditText inputFullName;
    private TextView txtDateOfBirth;
    private AutoCompleteTextView inputCountries;
    private EditText inputStory;
    private TextView txtDisplayCountries;
    private Button btnClear, btnInsert, btnChooseImage;
    private RadioButton radioMale, radioFemale, radioOther;

    private Bitmap bitImage;
    private List<String> countries;

    private static final int MY_REQUEST_CODE_SELECT_IMG = 1;
    private static final int THUMBNAIL_SIZE = 1280 * 720;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_directors, container, false);

        init(view);
        setEvent();

        return view;
    }

    private void init(View view) {
        img = view.findViewById(R.id.imgDirector);
        inputFullName = view.findViewById(R.id.inputFullNameDirector);
        txtDateOfBirth = view.findViewById(R.id.txtDateOfBirthDirector);
        inputCountries = view.findViewById(R.id.inputCountriesDirector);
        inputStory = view.findViewById(R.id.inputStoryDirector);
        txtDisplayCountries = view.findViewById(R.id.txtDisplayCountriesNameDirector);
        btnClear = view.findViewById(R.id.btnClearDirector);
        btnInsert = view.findViewById(R.id.btnInsertDirector);
        btnChooseImage = view.findViewById(R.id.btnChooseImageDirector);
        radioMale = view.findViewById(R.id.radioMaleDirector);
        radioFemale = view.findViewById(R.id.radioFemaleDirector);
        radioOther = view.findViewById(R.id.radioOtherDirector);

        bitImage = null;

        countries = new ArrayList<>();
        countries = getCountries();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.custom_item_text_auto, R.id.txtDisplayItem,  countries);
        inputCountries.setThreshold(1);
        inputCountries.setAdapter(adapter);
    }

    private List<String> getCountries() {
        List<String> countries = new ArrayList<>();

        String[] countryCodes = Locale.getISOCountries();

        for (String countryCode : countryCodes) {

            Locale obj = new Locale("", countryCode);

            countries.add(obj.getDisplayCountry().trim());

        }

        return countries;
    }

    private void setEvent() {
        btnChooseImage.setOnClickListener(v -> takePhotoFromDevice());
        txtDateOfBirth.setOnClickListener(v -> showDatePickerDialog());
        btnClear.setOnClickListener(v -> clearForm());
        btnInsert.setOnClickListener(v -> insert());
        inputCountries.setOnItemClickListener((parent, view, position, id) -> {
            String name = inputCountries.getText().toString().trim();
            setTextForTxtCountries(name);
        });
    }

    private void takePhotoFromDevice() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Title"), MY_REQUEST_CODE_SELECT_IMG);
        } catch (Exception e){
            Log.e("NOT CHOOSE IMAGE", "User not choose image from device!");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MY_REQUEST_CODE_SELECT_IMG){
            assert data != null;
            Uri uri = data.getData();
            img.setImageURI(uri);
            try {
                bitImage = getThumbnail(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Convert uri to bitmap
    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = getActivity().getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = getActivity().getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            txtDateOfBirth.setText(format.format(calendar.getTime()));
        }, nam, thang ,ngay);
        datePickerDialog.show();
    }

    private void clearForm() {
        inputCountries.setText("");
        inputStory.setText("");
        inputFullName.setText("");
        img.setImageResource(R.drawable.img_no_img);
        bitImage = null;
        txtDateOfBirth.setText("");
        txtDateOfBirth.setHint("Date of birth");
        txtDisplayCountries.setText("");
        txtDisplayCountries.setHint("Countries");
    }

    private void insert() {
        String fullName = inputFullName.getText().toString().trim();
        String dob = txtDateOfBirth.getText().toString().trim();
        String countries = txtDisplayCountries.getText().toString().trim();
        String gender = getGender();
        String story = inputStory.getText().toString().trim();

        boolean isNullInput = isNullInput(fullName, dob, countries, gender, story) || (bitImage == null);
        if (isNullInput) {
            Toast.makeText(getActivity(), "Please complete all information!", Toast.LENGTH_SHORT).show();  return;
        }

        Director directorSearch = null;
        directorSearch = DirectorDatabase.getInstance(getActivity()).directorDAO().searchDirectorByName(fullName);
        if (directorSearch != null) {
            Toast.makeText(getActivity(), "The name already exists in the list!", Toast.LENGTH_SHORT).show(); return;
        }

        Director director = new Director(ConvertImageStore.imagemTratada(ConvertImageStore.convertImageToByArr(bitImage)),
                fullName, dob, countries, gender, story);
        DirectorDatabase.getInstance(getActivity()).directorDAO().insert(director);
        Toast.makeText(getActivity(), "Insertion successfully!", Toast.LENGTH_SHORT).show();
        clearForm();
        displayKeyboard();
    }

    private void displayKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private String getGender() {
        if (radioMale.isChecked()) {
            return "Male";
        } else if (radioFemale.isChecked()) {
            return "Female";
        } else if (radioOther.isChecked()) {
            return "Other";
        }

        return "";
    }

    private boolean isNullInput(String... input) {
        for (int i=0; i<input.length; i++) {
            if (input[i].isEmpty()) return true;
        }
        return false;
    }

    private void setTextForTxtCountries(String input) {
        txtDisplayCountries.setText("");
        txtDisplayCountries.setText(input);
    }
}