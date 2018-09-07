package jp.android_group.asj.enpit_sample12.model;


import android.os.Parcel;
import android.os.Parcelable;

public class EntryModel implements Parcelable {
    // DB定義
    private int id;
    private String title;
    private String body;
    private String create_date;
    private String status;
    private int author_id;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(title);
        out.writeString(body);
        out.writeString(create_date);
        out.writeString(status);
        out.writeInt(author_id);
    }

    public static final Parcelable.Creator<EntryModel> CREATOR = new Parcelable.Creator<EntryModel>() {
        public EntryModel createFromParcel(Parcel in) {
            return new EntryModel(in);
        }

        public EntryModel[] newArray(int size) {
            return new EntryModel[size];
        }
    };

    private EntryModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        body = in.readString();
        create_date = in.readString();
        status = in.readString();
        author_id = in.readInt();
    }

    public EntryModel(int id, String title, String body, String status, String create_date, int author_id) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.create_date = create_date;
        this.status = status;
        this.author_id = author_id;
    }

    public EntryModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    @Override
    public String toString() {
        return "EntryModel [id=" + id + ", title=" + title + ", body=" + body +
                ", create at=" + create_date +
                ", status=" + status + ", author_id=" + author_id + "]";
    }
}

