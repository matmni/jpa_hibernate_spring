package entity.persistence;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Component
public class Address {

    private String street;
    private String city;
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    public Address() {
    }

    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
