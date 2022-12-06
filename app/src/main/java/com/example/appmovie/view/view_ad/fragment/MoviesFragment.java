package com.example.appmovie.view.view_ad.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmovie.R;
import com.example.appmovie.class_supported.ConvertImageStore;
import com.example.appmovie.db.db_actor.ActorDatabase;
import com.example.appmovie.db.db_director.DirectorDatabase;
import com.example.appmovie.db.db_movie.MovieDatabase;
import com.example.appmovie.model.Actor;
import com.example.appmovie.model.Director;
import com.example.appmovie.model.Movie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
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

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    private ImageView poster;
    private Button btnChooseImage;
    private EditText inputLinkTrailer, inputLinkMovie, inputMovieName;
    private AutoCompleteTextView inputDirectors, inputActors;
    private TextView txtDate;
    private Spinner spnCategory;
    private EditText inputSummary;
    private EditText inputTime, inputLimitAge, inputPoint;
    private Button btnClear, btnInsert;
    private ImageView imgDeleteDirectorsName, imgDeleteActorsName;
    private TextView txtDisplayDirectorName, txtDisplayActorName;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<String> categories;
    private List<String> actorsName, directorsName;
    private List<String> actorSelected, directorSelected;
    private Bitmap bitPoster;

    private static final int MY_REQUEST_CODE_SELECT_IMG = 1;
    private static final int THUMBNAIL_SIZE = 1280 * 720;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        init(view);
        setDataForSpn();
        activeAutoCompleteText();
        setEvent();

        return view;
    }

    private void init(View view) {
        poster = view.findViewById(R.id.imgMovie);
        btnChooseImage = view.findViewById(R.id.btnChoosePosterMovie);
        inputLinkTrailer = view.findViewById(R.id.inputLinkTrailer);
        inputLinkMovie = view.findViewById(R.id.inputLinkFilm);
        inputMovieName = view.findViewById(R.id.inputMovieName);
        inputDirectors = view.findViewById(R.id.inputDirectorsName);
        inputActors = view.findViewById(R.id.inputActorsName);
        txtDate = view.findViewById(R.id.txtDateMovie);
        spnCategory = view.findViewById(R.id.spnCategory);
        inputSummary = view.findViewById(R.id.inputSummary);
        inputTime = view.findViewById(R.id.inputTimeMovie);
        inputLimitAge = view.findViewById(R.id.inputLimitAge);
        inputPoint = view.findViewById(R.id.inputPoint);
        btnClear = view.findViewById(R.id.btnClearMovie);
        btnInsert = view.findViewById(R.id.btnInsertMovie);
        imgDeleteActorsName = view.findViewById(R.id.imgDeleteActorName);
        imgDeleteDirectorsName = view.findViewById(R.id.imgDeleteDirectorName);
        txtDisplayActorName = view.findViewById(R.id.txtDisplayActorName);
        txtDisplayDirectorName = view.findViewById(R.id.txtDisplayDirectorName);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(this);

        categories = new ArrayList<>();
        actorSelected = new ArrayList<>();
        directorSelected = new ArrayList<>();

        categories = getCategories();
        loadData();
        bitPoster = null;
    }

    private List<String> getCategories() {
        List<String> list = new ArrayList<>();

        list.add("Comedy Movies");
        list.add("Action Movies");
        list.add("Sci-fi Movies");
        list.add("Horror Movies");
        list.add("War Movies");
        list.add("Romance Movies");
        list.add("Musical Film");
        list.add("Film Noir");
        list.add("Animated Movies");
        list.add("Crime Movies");
        list.add("Drama Movies");

        return list;
    }

    private void setDataForSpn() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
        spnCategory.setAdapter(adapter);
    }

    private void activeAutoCompleteText() {
        loadData();
        ArrayAdapter<String> directorArrayAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.custom_item_text_auto,
                R.id.txtDisplayItem, directorsName);
        inputDirectors.setThreshold(1);
        inputDirectors.setAdapter(directorArrayAdapter);

        ArrayAdapter<String> actorArrayAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.custom_item_text_auto,
                R.id.txtDisplayItem, actorsName);
        inputActors.setThreshold(1);
        inputActors.setAdapter(actorArrayAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setEvent() {
        btnChooseImage.setOnClickListener(v -> takePhoto());
        btnClear.setOnClickListener(v -> clear());
        btnInsert.setOnClickListener(v -> insert());
        txtDate.setOnClickListener(v -> showDatePickerDialog());
        imgDeleteDirectorsName.setOnClickListener(v -> deleteDirectorName());
        imgDeleteActorsName.setOnClickListener(v -> deleteActorName());
        inputActors.setOnItemClickListener((parent, view, position, id) -> getActorsName());
        inputDirectors.setOnItemClickListener((parent, view, position, id) -> getDirectorsName());
    }

    private void takePhoto() {
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
            poster.setImageURI(uri);
            try {
                bitPoster = getThumbnail(uri);
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
            txtDate.setText(format.format(calendar.getTime()));
        }, nam, thang ,ngay);
        datePickerDialog.show();
    }
    
    private void clear() {
        poster.setImageResource(R.drawable.img_no_img);
        bitPoster = null;
        inputLinkTrailer.setText("");
        inputLinkTrailer.setHint("Link Trailer");
        inputLinkMovie.setText("");
        inputLinkMovie.setHint("Link Movie");
        inputMovieName.setText("");
        inputDirectors.setText("");
        inputActors.setText("");
        txtDate.setText("");
        txtDate.setHint("dd/MM/yyyy");
        spnCategory.setSelection(0);
        inputSummary.setText("");
        inputSummary.setHint("Summary");
        inputTime.setText("");
        inputTime.setHint("Time(m)");
        inputLimitAge.setText("");
        inputLimitAge.setHint("Limit age");
        inputPoint.setText("");
        inputPoint.setHint("Point");

        txtDisplayActorName.setText("");
        txtDisplayDirectorName.setText("");
        txtDisplayActorName.setHint("Actor name");
        txtDisplayDirectorName.setHint("Director name");
        txtDisplayActorName.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        txtDisplayDirectorName.setHintTextColor(getResources().getColor(R.color.colorPrimary));

        actorSelected = new ArrayList<>();
        directorSelected = new ArrayList<>();
    }
    
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void insert() {
        String linkTrailer = inputLinkTrailer.getText().toString().trim();
        String linkMovie = inputLinkMovie.getText().toString().trim();
        String movieName = inputMovieName.getText().toString().trim();
        String listDirector = txtDisplayDirectorName.getText().toString().trim();
        String listActorName = txtDisplayActorName.getText().toString().trim();
        String date = txtDate.getText().toString().trim();
        String category = spnCategory.getSelectedItem().toString().trim();
        String summary = inputSummary.getText().toString().trim();
        String timeText = inputTime.getText().toString().trim();
        String ageLimitText = inputLimitAge.getText().toString().trim();
        String pointText = inputPoint.getText().toString().trim();
        
        boolean isNullInput = isNullInput(linkTrailer, linkMovie, movieName, listActorName, listDirector, date, summary, timeText, ageLimitText, pointText) || (bitPoster == null);
        if (isNullInput) {
            Toast.makeText(getActivity(), "Please complete all information!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUrlTrailer = isUrl(linkTrailer);
        boolean isUrlMovie = isUrl(linkMovie);
        if (!isUrlTrailer) {
            Toast.makeText(getActivity(), "The trailer link is malformed!", Toast.LENGTH_SHORT).show();
            return;
        } if (!isUrlMovie) {
            Toast.makeText(getActivity(), "The movie link is not in the correct format!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        int time = 0, ageLimit = 13;
        double point = 0.0;
        try {
            time = Integer.valueOf(timeText);
            ageLimit = Integer.valueOf(ageLimitText);
            point = Double.valueOf(pointText);
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Please double check the input data!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (time == 0) {
            Toast.makeText(getActivity(), "Invalid time!", Toast.LENGTH_SHORT).show();
            return;
        } if (ageLimit < 13) {
            Toast.makeText(getActivity(), "Minimum age must be greater than or equal to 13!", Toast.LENGTH_SHORT).show();
            return;
        } if (point > 10.0) {
            Toast.makeText(getActivity(), "Invalid score!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Khong cho nguoi dung chon 2 dao dien giong nhau va 2 dien vien giong nhau
        String[] actorsName = listActorName.split(",");
        String[] directorsName = listDirector.split(",");
        // xoa bo khoang trang dau cuoi
        for (int i=0; i<actorsName.length; ++i) {
            actorsName[i] = actorsName[i].trim();
        } for (int i=0; i<directorsName.length; ++i) {
            directorsName[i] = directorsName[i].trim();
        }
        String[] arrActor = Arrays.stream(actorsName).distinct().toArray(String[]::new);
        String[] arrDirector = Arrays.stream(directorsName).distinct().toArray(String[]::new);
        listActorName = "";
        listDirector = "";
        for (int i=0; i<arrActor.length; i++) {
            listActorName += arrActor[i] + ", ";
        } for (int i=0; i< arrDirector.length; i++) {
            listDirector += arrDirector[i] + ", ";
        }

        Movie movie = new Movie(ConvertImageStore.imagemTratada(ConvertImageStore.convertImageToByArr(bitPoster)),
                linkTrailer, linkMovie, movieName, listDirector, listActorName,
                date, category, summary, time, ageLimit, point);
        MovieDatabase.getInstance(getActivity()).movieDAO().insert(movie);
        Toast.makeText(getActivity(), "Insertion successfully!", Toast.LENGTH_SHORT).show();
        clear();
    }
    
    private boolean isNullInput(String... input) {
        for (int i=0; i<input.length; i++) {
            if (input[i].isEmpty()) return true;
        }
        
        return false;
    }

    private boolean isUrl(String input) {
        URL u = null;
        boolean result = false;
        try {
            u = new URL(input);
            u.toURI();

            result = true;
        } catch (MalformedURLException e) {
            result = false;
        } catch (URISyntaxException e) {
            result = false;
        }

        return result;
    }
    
    private void getDirectorsName() {
        String directorsName = inputDirectors.getText().toString().trim()+", ";
        directorSelected.add(directorsName);
        String temp = "";
        for (String s : directorSelected) {
            temp += s;
        }

        txtDisplayDirectorName.setText(temp);
        inputDirectors.setText("");
        inputDirectors.setHint("Directors name");
    }
    
    private void getActorsName() {
        String actorsName = inputActors.getText().toString().trim()+", ";
        actorSelected.add(actorsName);
        String temp = "";
        for (String s : actorSelected) {
            temp += s;
        }
        txtDisplayActorName.setText(temp);
        inputActors.setText("");
        inputActors.setHint("Actors name");
    }
    
    private void deleteDirectorName() {
        if (directorSelected.size() == 0) return;

        directorSelected.remove(directorSelected.size() - 1);
        String temp = "";
        for (String s : directorSelected) {
            temp += s;
        }

        if (directorSelected.size() == 0) {
            txtDisplayDirectorName.setText("");
            txtDisplayDirectorName.setHint("Director name");
            txtDisplayDirectorName.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            txtDisplayDirectorName.setText(temp);
        }
    }
    
    private void deleteActorName() {
        if (actorSelected.size() == 0) return;

        actorSelected.remove(actorSelected.size()-1);
        String temp = "";
        for (String s : actorSelected) {
            temp += s;
        }

        if (actorSelected.size() == 0) {
            txtDisplayActorName.setText("");
            txtDisplayActorName.setHint("Actor name");
            txtDisplayActorName.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            txtDisplayActorName.setText(temp);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activeAutoCompleteText();
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            activeAutoCompleteText();
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), "Data refresh successful!", Toast.LENGTH_SHORT).show();
        }, 3000);
    }

    private void loadData() {
        actorsName = new ArrayList<>();
        directorsName = new ArrayList<>();

        directorsName = DirectorDatabase.getInstance(getActivity()).directorDAO().getDirectorsName();
        actorsName = ActorDatabase.getInstance(getActivity()).actorDAO().getActorName();
    }
}