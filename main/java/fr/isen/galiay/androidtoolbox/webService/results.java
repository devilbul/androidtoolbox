package fr.isen.galiay.androidtoolbox.webService;

import static fr.isen.galiay.androidtoolbox.util.Util.toFirstLetterUpperCase;

public class results {
    private String gender;
    private String email;
    private String dob;
    private String registered;
    private String phone;
    private String cell;
    private String nat;
    private name name;
    private location location;
    private login login;
    private id id;
    private picture picture;

    public results(String gender, String email, String dob, String registered, String phone, String cell, String nat, results.name name, results.location location, results.login login, results.id id, results.picture picture) {
        this.gender = gender;
        this.email = email;
        this.dob = dob;
        this.registered = registered;
        this.phone = phone;
        this.cell = cell;
        this.nat = nat;
        this.name = name;
        this.location = location;
        this.login = login;
        this.id = id;
        this.picture = picture;
    }

    public class name {
        private String title;
        private String first;
        private String last;

        public name(String title, String first, String last) {
            this.title = title;
            this.first = first;
            this.last = last;
        }

        public String getTitle() {
            return toFirstLetterUpperCase(title);
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLast() {
            return last.toUpperCase();
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getFirst() {
            return toFirstLetterUpperCase(first);
        }

        public void setFirst(String first) {
            this.first = first;
        }
    }

    public class location {
        private String street;
        private String city;
        private String state;
        private String postcode;

        public location(String street, String city, String state, String postcode) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.postcode = postcode;
        }

        public String getStreet() {
            return toFirstLetterUpperCase(street);
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return toFirstLetterUpperCase(city);
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return toFirstLetterUpperCase(state);
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostcode() {
            return toFirstLetterUpperCase(postcode);
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }
    }

    public class login {
        private String username;
        private String password;
        private String salt;
        private String md5;
        private String sha1;
        private String sha256;

        public login(String username, String password, String salt, String md5, String sha1, String sha256) {
            this.username = username;
            this.password = password;
            this.salt = salt;
            this.md5 = md5;
            this.sha1 = sha1;
            this.sha256 = sha256;
        }

        public String getUsername() {
            return toFirstLetterUpperCase(username);
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return toFirstLetterUpperCase(password);
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSalt() {
            return toFirstLetterUpperCase(salt);
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getMd5() {
            return toFirstLetterUpperCase(md5);
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getSha1() {
            return toFirstLetterUpperCase(sha1);
        }

        public void setSha1(String sha1) {
            this.sha1 = sha1;
        }

        public String getSha256() {
            return toFirstLetterUpperCase(sha256);
        }

        public void setSha256(String sha256) {
            this.sha256 = sha256;
        }
    }

    public class id {
        private String name;
        private String value;

        public id(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return toFirstLetterUpperCase(name);
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return toFirstLetterUpperCase(value);
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public class picture {
        private String large;
        private String medium;
        private String thumbnail;

        public picture(String large, String medium, String thumbnail) {
            this.large = large;
            this.medium = medium;
            this.thumbnail = thumbnail;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }

    public results.picture getPicture() {
        return picture;
    }

    public void setPicture(results.picture picture) {
        this.picture = picture;
    }

    public results.id getId() {
        return id;
    }

    public void setId(results.id id) {
        this.id = id;
    }

    public results.login getLogin() {
        return login;
    }

    public void setLogin(results.login login) {
        this.login = login;
    }

    public results.location getLocation() {
        return location;
    }

    public void setLocation(results.location location) {
        this.location = location;
    }

    public results.name getName() {
        return name;
    }

    public void setName(results.name name) {
        this.name = name;
    }

    public String getNat() {
        return toFirstLetterUpperCase(nat);
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public String getGender() {
        return toFirstLetterUpperCase(gender);
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return toFirstLetterUpperCase(dob);
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRegistered() {
        return toFirstLetterUpperCase(registered);
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }
}

