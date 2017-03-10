package alc.javadevslagos.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import alc.javadevslagos.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class DeveloperDetailsActivity extends AppCompatActivity {

    private CircleImageView circleImageView;
    private TextView username, link;
    private Button button_share;
    
    private String intent_username, intent_link, intent_avatar_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_details);

        circleImageView = (CircleImageView) findViewById(R.id.imageview_avatar);
        username = (TextView) findViewById(R.id.textview_username);
        link = (TextView) findViewById(R.id.textview_link);
        button_share = (Button) findViewById(R.id.button_share);


        Intent intent = getIntent();
        intent_username = intent.getExtras().getString("username");
        intent_avatar_url = intent.getExtras().getString("avatar_url");
        intent_link = intent.getExtras().getString("link");

        username.setText(intent_username);
        link.setText(intent_link);
        Picasso.with(getApplicationContext())
                .load(intent_avatar_url)
                .placeholder(R.mipmap.placeholder)
                .resize(80, 80)
                .centerCrop()
                .into(circleImageView);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchWebIntent(intent_link);
            }
        });
        
        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDeveloperDetails();
            }
        });

    }

    private void shareDeveloperDetails() {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = "Check out this awesome developer @" + intent_username + ", " + intent_link;
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void launchWebIntent(String url) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(webIntent);
    }
    
    
}
