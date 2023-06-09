package com.virmana.status_app_all.ui.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import com.virmana.status_app_all.R;
import com.virmana.status_app_all.api.apiClient;
import com.virmana.status_app_all.api.apiRest;
import com.virmana.status_app_all.model.ApiResponse;
import com.virmana.status_app_all.Provider.PrefManager;
import com.virmana.status_app_all.ui.view.OtpEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{


    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    private LoginButton sign_in_button_facebook;
    private SignInButton sign_in_button_google;

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;

    private ProgressDialog register_progress;
    private TextView text_view_skip_login;
    private RelativeLayout relative_layout_google_login;
    private RelativeLayout relative_layout_phone_login;
    String VerificationCode = "";
    private EditText editText;
    private CountryCodePicker countryCodePicker;
    private RelativeLayout relative_layout_confirm_phone_number;
    private EditText edit_text_otp_1;
    private EditText edit_text_otp_2;
    private EditText edit_text_otp_3;
    private EditText edit_text_otp_4;
    private EditText edit_text_otp_5;
    private EditText edit_text_otp_6;
    private OtpEditText otp_edit_text_login_activity;
    private RelativeLayout relative_layout_confirm_top_login_activity;
    private EditText edit_text_phone_number_login_acitivty;
    private LinearLayout linear_layout_buttons_login_activity;
    private LinearLayout linear_layout_otp_confirm_login_activity;
    private LinearLayout linear_layout_phone_input_login_activity;
    private RelativeLayout relative_layout_confirm_full_name;
    private LinearLayout linear_layout_name_input_login_activity;
    private EditText edit_text_name_login_acitivty;
    private String phoneNum ="";
    private String token ="";
    private PrefManager prf;

    private EditText edit_text_reference_code;
    private RelativeLayout relative_layout_reference_coode;
    private Button btn_send_code;
    private boolean referenceCodeAsked = false;
    private boolean askReferenceDialog = false;
    private AppCompatCheckBox check_box_login_activity_privacy;
    private TextView text_view_login_activity_privacy;


    private FirebaseAuth mAuth;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        prf= new PrefManager(getApplicationContext());
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        LoginActivity.this.token = task.getResult();

                    }
                });
        if (prf.getString("LOGGED").toString().equals("TRUE")){
            Intent intent= new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        initView();
        initAction();
        FaceookSignIn();
        GoogleSignIn();
        mAuth = FirebaseAuth.getInstance();

    }


    public void initView(){

        this.btn_send_code   =      (Button)  findViewById(R.id.btn_send_code);
        this.edit_text_reference_code   =      (EditText)  findViewById(R.id.edit_text_reference_code);
        this.relative_layout_reference_coode   =      (RelativeLayout)  findViewById(R.id.relative_layout_reference_coode);

        this.edit_text_name_login_acitivty   =      (EditText)  findViewById(R.id.edit_text_name_login_acitivty);
        this.edit_text_phone_number_login_acitivty   =      (EditText)  findViewById(R.id.edit_text_phone_number_login_acitivty);
        this.otp_edit_text_login_activity   =      (OtpEditText)  findViewById(R.id.otp_edit_text_login_activity);
        this.relative_layout_confirm_top_login_activity   =      (RelativeLayout)  findViewById(R.id.relative_layout_confirm_top_login_activity);
        this.relative_layout_google_login   =      (RelativeLayout)  findViewById(R.id.relative_layout_google_login);
        this.sign_in_button_google   =      (SignInButton)  findViewById(R.id.sign_in_button_google);
        this.sign_in_button_facebook =      (LoginButton)   findViewById(R.id.sign_in_button_facebook);
        this.relative_layout_phone_login =      (RelativeLayout)   findViewById(R.id.relative_layout_phone_login);
        this.relative_layout_confirm_phone_number =      (RelativeLayout)   findViewById(R.id.relative_layout_confirm_phone_number);
        this.linear_layout_buttons_login_activity =      (LinearLayout)   findViewById(R.id.linear_layout_buttons_login_activity);
        this.linear_layout_otp_confirm_login_activity =      (LinearLayout)   findViewById(R.id.linear_layout_otp_confirm_login_activity);
        this.linear_layout_phone_input_login_activity =      (LinearLayout)   findViewById(R.id.linear_layout_phone_input_login_activity);
        this.linear_layout_name_input_login_activity =      (LinearLayout)   findViewById(R.id.linear_layout_name_input_login_activity);
        this.relative_layout_confirm_full_name =      (RelativeLayout)   findViewById(R.id.relative_layout_confirm_full_name);

        this.countryCodePicker =      (CountryCodePicker)   findViewById(R.id.CountryCodePicker);

        this.text_view_login_activity_privacy = (TextView) findViewById(R.id.text_view_login_activity_privacy);
        this.check_box_login_activity_privacy = (AppCompatCheckBox) findViewById(R.id.check_box_login_activity_privacy);
    }
    public void initAction(){
        this.text_view_login_activity_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,PolicyActivity.class));
            }
        });
        this.check_box_login_activity_privacy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sign_in_button_facebook.setEnabled(true);
                    check_box_login_activity_privacy.setError(null);
                    relative_layout_phone_login.setAlpha(1);
                    relative_layout_google_login.setAlpha(1);
                }else{
                    sign_in_button_facebook.setEnabled(false);
                    relative_layout_phone_login.setAlpha((float) 0.7);
                    relative_layout_google_login.setAlpha((float) 0.7);
                }
            }
        });
        btn_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_text_reference_code.getText().toString().trim().length()<4){
                    Toasty.error(LoginActivity.this, "Reference key very short try to use connect one ", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendCode();
            }
        });
        relative_layout_confirm_full_name.setOnClickListener(v->{
            String token_user =  prf.getString("TOKEN_USER");
            String id_user =  prf.getString("ID_USER");
            if (edit_text_name_login_acitivty.getText().toString().length()<3) {
                Toasty.error(getApplicationContext(), "This name very shot ", Toast.LENGTH_LONG).show();
                return;
            }
            askReferenceDialog =  true;
            updateToken(Integer.parseInt(id_user),token_user,token,edit_text_name_login_acitivty.getText().toString());

        });
        relative_layout_confirm_top_login_activity.setOnClickListener(v->{
            if(otp_edit_text_login_activity.getText().toString().length()<6){
                Toasty.error(this, "The verification code you have been entered incorrect !", Toast.LENGTH_SHORT).show();
            }else{
                verifyCode(otp_edit_text_login_activity.getText().toString());
            }
        });

        this.relative_layout_phone_login.setOnClickListener(v -> {
            if (!check_box_login_activity_privacy.isChecked()){
                check_box_login_activity_privacy.setError(getResources().getString(R.string.accept_privacy_policy_error));
                return;
            }
            linear_layout_buttons_login_activity.setVisibility(View.GONE);
            linear_layout_phone_input_login_activity.setVisibility(View.VISIBLE);
        });
        relative_layout_confirm_phone_number.setOnClickListener(v ->{
            phoneNum = "+"+countryCodePicker.getSelectedCountryCode().toString()+edit_text_phone_number_login_acitivty.getText().toString();

            new AlertDialog.Builder(this)
                    .setTitle("We will be verifying the phone number:"  )
                    .setMessage(" \n"+phoneNum+" \n\n Is this OK,or would you like to edit the number ?")
                    .setPositiveButton("Confrim",
                            (dialog, which) -> {
                                //Do Something Here
                                loginWithPhone();

                            })
                    .setNegativeButton("Edit",
                            (dialog, which) -> {
                                dialog.dismiss();
                            }).show();
        });
        relative_layout_google_login.setOnClickListener(view -> {
            if (!check_box_login_activity_privacy.isChecked()){
                check_box_login_activity_privacy.setError(getResources().getString(R.string.accept_privacy_policy_error));
                return;
            }
            signIn();
        });
        this.sign_in_button_google.setOnClickListener(view -> {
            if (!check_box_login_activity_privacy.isChecked()){
                check_box_login_activity_privacy.setError(getResources().getString(R.string.accept_privacy_policy_error));
                return;
            }
            signIn();
        });


    }

    private void loginWithPhone() {
        linear_layout_phone_input_login_activity.setVisibility(View.GONE);
        linear_layout_otp_confirm_login_activity.setVisibility(View.VISIBLE);


        sendVerificationCode(phoneNum);
    }
    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
            Log.v("PHONE",s);
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();
            Log.v("PHONE code ",code);

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                otp_edit_text_login_activity.setText(code);
                VerificationCode =  code;

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String photo = "https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg" ;
                            signUp(phoneNum,phoneNum,"null".toString(),"phone",photo);
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            getResultGoogle(result);
        }
    }
    public void GoogleSignIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
    public void FaceookSignIn(){

        // Other app specific specializationsign_in_button_facebook.setReadPermissions(Arrays.asList("public_profile"));
        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        sign_in_button_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        getResultFacebook(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                set(LoginActivity.this, "Operation has been cancelled ! ");
            }

            @Override
            public void onError(FacebookException exception) {
                set(LoginActivity.this, "Operation has been cancelled ! ");
            }
        });
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);

    }
    private void getResultGoogle(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {

            GoogleSignInAccount acct = result.getSignInAccount();
            String photo = "https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg" ;
            if (acct.getPhotoUrl()!=null){
                photo =  acct.getPhotoUrl().toString();
            }

            signUp(acct.getId().toString(),acct.getId(), acct.getDisplayName().toString(),"google",photo);
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        } else {
        }
    }
    private void getResultFacebook(JSONObject object){
        Log.d(TAG, object.toString());
        try {
            signUp(object.getString("id").toString(),object.getString("id").toString(),object.getString("name").toString(),"facebook",object.getJSONObject("picture").getJSONObject("data").getString("url"));
            LoginManager.getInstance().logOut();        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    public void signUp(String username,String password,String name,String type,String image){
        register_progress= ProgressDialog.show(this, null,getResources().getString(R.string.operation_progress), true);
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<ApiResponse> call = service.register(name,username,password,type,image);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.body()!=null){
                    if (response.body().getCode()==200){

                        String id_user="0";
                        String name_user="x";
                        String username_user="x";
                        String salt_user="0";
                        String token_user="0";
                        String type_user="x";
                        String image_user="x";
                        String enabled="x";
                        String registered="x";
                        for (int i=0;i<response.body().getValues().size();i++){
                            if (response.body().getValues().get(i).getName().equals("salt")){
                                salt_user=response.body().getValues().get(i).getValue();
                            }
                            if (response.body().getValues().get(i).getName().equals("token")){
                                token_user=response.body().getValues().get(i).getValue();
                            }
                            if (response.body().getValues().get(i).getName().equals("id")){
                                id_user=response.body().getValues().get(i).getValue();
                            }
                            if (response.body().getValues().get(i).getName().equals("name")){
                                name_user=response.body().getValues().get(i).getValue();
                            }
                            if (response.body().getValues().get(i).getName().equals("type")){
                                type_user=response.body().getValues().get(i).getValue();
                            }
                            if (response.body().getValues().get(i).getName().equals("username")){
                                username_user=response.body().getValues().get(i).getValue();
                            }
                            if (response.body().getValues().get(i).getName().equals("url")){
                                image_user=response.body().getValues().get(i).getValue();
                            }
                            if (response.body().getValues().get(i).getName().equals("enabled")){
                                enabled=response.body().getValues().get(i).getValue();
                            }
                            if (response.body().getValues().get(i).getName().equals("registered")){
                                registered=response.body().getValues().get(i).getValue();
                            }
                        }if (enabled.equals("true")){
                            PrefManager prf= new PrefManager(getApplicationContext());
                            prf.setString("ID_USER",id_user);
                            prf.setString("SALT_USER",salt_user);
                            prf.setString("TOKEN_USER",token_user);
                            prf.setString("NAME_USER",name_user);
                            prf.setString("TYPE_USER",type_user);
                            prf.setString("USERN_USER",username_user);
                            prf.setString("IMAGE_USER",image_user);
                            prf.setString("LOGGED","TRUE");
                            if (name_user.toLowerCase().equals("null")){
                                linear_layout_otp_confirm_login_activity.setVisibility(View.GONE);
                                linear_layout_name_input_login_activity.setVisibility(View.VISIBLE);
                            }else{
                                if (registered.equals("true")){
                                    if (prf.getString("EARNING_SYSTEM").equals("TRUE")){
                                        relative_layout_reference_coode.setVisibility(View.VISIBLE);
                                        linear_layout_buttons_login_activity.setVisibility(View.GONE);
                                        referenceCodeAsked = true;
                                    }
                                }
                                updateToken(Integer.parseInt(id_user),token_user,token,name_user);
                            }
                        }else{
                            Toasty.error(getApplicationContext(),getResources().getString(R.string.account_disabled), Toast.LENGTH_SHORT, true).show();
                        }
                    }
                    if (response.body().getCode()==500){
                        Toasty.error(getApplicationContext(), "Operation has been cancelled ! ", Toast.LENGTH_SHORT, true).show();
                    }
                }else{
                    Toasty.error(getApplicationContext(), "Operation has been cancelled ! ", Toast.LENGTH_SHORT, true).show();
                }
                register_progress.dismiss();
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Operation has been cancelled ! ", Toast.LENGTH_SHORT, true).show();
                register_progress.dismiss();
            }
        });
    }
    public static void set(Activity activity, String s){
        Toasty.error(activity,s,Toast.LENGTH_LONG).show();
        activity.finish();
    }
    public void updateToken(Integer id,String key,String token,String name){
        if (askReferenceDialog)
            register_progress= ProgressDialog.show(this, null,getResources().getString(R.string.operation_progress), true);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<ApiResponse> call = service.editToken(id,key,token,name);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                prf.setString("NAME_USER",name );
                register_progress.dismiss();
                if (response.isSuccessful()){
                    Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                }
                if (askReferenceDialog){
                    if (prf.getString("EARNING_SYSTEM").equals("TRUE")){
                        relative_layout_reference_coode.setVisibility(View.VISIBLE);
                        linear_layout_buttons_login_activity.setVisibility(View.GONE);
                        linear_layout_otp_confirm_login_activity.setVisibility(View.GONE);
                        linear_layout_name_input_login_activity.setVisibility(View.GONE);
                        referenceCodeAsked = true;
                    }else{
                        finish();
                    }
                }
                if (!referenceCodeAsked){
                    finish();
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                register_progress.dismiss();
                Toasty.error(getApplicationContext(), "Operation has been cancelled ! ", Toast.LENGTH_SHORT, true).show();
                if (askReferenceDialog){
                    if (prf.getString("EARNING_SYSTEM").equals("TRUE")){
                        relative_layout_reference_coode.setVisibility(View.VISIBLE);
                        linear_layout_buttons_login_activity.setVisibility(View.GONE);
                        linear_layout_otp_confirm_login_activity.setVisibility(View.GONE);
                        linear_layout_name_input_login_activity.setVisibility(View.GONE);
                        referenceCodeAsked = true;
                    }else{
                        finish();
                    }
                }
                if (!referenceCodeAsked){
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){

        super.onBackPressed();
        return;
    }

    public void sendCode(){
        final PrefManager prefManager = new PrefManager(this);
        Integer id_user = 0;
        String key_user= "";
        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
            id_user=  Integer.parseInt(prefManager.getString("ID_USER"));
            key_user=  prefManager.getString("TOKEN_USER");
        }
        register_progress = new ProgressDialog(LoginActivity.this);
        register_progress.setCancelable(true);
        register_progress.setMessage(getResources().getString(R.string.operation_progress));
        register_progress.show();
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<ApiResponse> call = service.sendRefereceCode(id_user,key_user,edit_text_reference_code.getText().toString().trim());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                register_progress.dismiss();
                if (response.isSuccessful()){
                    if (response.body().getCode().equals(200)) {
                        Toasty.success(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        finish();
                    }else{
                        Toasty.error(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                register_progress.dismiss();
                Toasty.error(getApplicationContext(), "Operation has been cancelled ! ", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                super.onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

