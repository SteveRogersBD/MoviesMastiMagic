package com.example.movies.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PopularSeriesResponse implements Parcelable {
    public int page;
    public ArrayList<Result> results;
    public int total_pages;
    public int total_results;

    protected PopularSeriesResponse(Parcel in) {
        page = in.readInt();
        total_pages = in.readInt();
        total_results = in.readInt();
        results = in.createTypedArrayList(Result.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(total_pages);
        dest.writeInt(total_results);
        dest.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PopularSeriesResponse> CREATOR = new Creator<PopularSeriesResponse>() {
        @Override
        public PopularSeriesResponse createFromParcel(Parcel in) {
            return new PopularSeriesResponse(in);
        }

        @Override
        public PopularSeriesResponse[] newArray(int size) {
            return new PopularSeriesResponse[size];
        }
    };

    public static class Result implements Parcelable {
        public boolean adult;
        public String backdrop_path;
        public ArrayList<Integer> genre_ids;
        public int id;
        public ArrayList<String> origin_country;
        public String original_language;
        public String original_name;
        public String overview;
        public double popularity;
        public String poster_path;
        public String first_air_date;
        public String name;
        public double vote_average;
        public int vote_count;

        protected Result(Parcel in) {
            adult = in.readByte() != 0;
            backdrop_path = in.readString();
            genre_ids = new ArrayList<>();
            in.readList(genre_ids, Integer.class.getClassLoader());
            id = in.readInt();
            origin_country = in.createStringArrayList();
            original_language = in.readString();
            original_name = in.readString();
            overview = in.readString();
            popularity = in.readDouble();
            poster_path = in.readString();
            first_air_date = in.readString();
            name = in.readString();
            vote_average = in.readDouble();
            vote_count = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte((byte) (adult ? 1 : 0));
            dest.writeString(backdrop_path);
            dest.writeList(genre_ids);
            dest.writeInt(id);
            dest.writeStringList(origin_country);
            dest.writeString(original_language);
            dest.writeString(original_name);
            dest.writeString(overview);
            dest.writeDouble(popularity);
            dest.writeString(poster_path);
            dest.writeString(first_air_date);
            dest.writeString(name);
            dest.writeDouble(vote_average);
            dest.writeInt(vote_count);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Result> CREATOR = new Creator<Result>() {
            @Override
            public Result createFromParcel(Parcel in) {
                return new Result(in);
            }

            @Override
            public Result[] newArray(int size) {
                return new Result[size];
            }
        };
    }
}

