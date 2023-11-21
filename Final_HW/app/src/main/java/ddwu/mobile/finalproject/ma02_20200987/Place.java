package ddwu.mobile.finalproject.ma02_20200987;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Place implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String placeId;
    public String name;
    public String phoneNumber;
    public String address;
    public String type;
    public String uri;

    public String memo;

    public Place() {

    }

    public Place(String placeId, String name, String phoneNumber, String address, String type, String memo) {
        this.placeId = placeId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.type = type;
        this.memo = memo;
    }

    public Place(String placeId, String name, String phoneNumber, String address, String type, String uri, String memo) {
        this.placeId = placeId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.type = type;
        this.uri = uri;
        this.memo = memo;
    }

    public long get_Id() {
        return id;
    }

    public void set_Id(long _id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return name;
    }
}
