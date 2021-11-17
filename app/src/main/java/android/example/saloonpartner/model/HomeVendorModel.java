package android.example.saloonpartner.model;


public class HomeVendorModel {
    public HomeVendorModel() {
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankadress() {
        return bankadress;
    }

    public void setBankadress(String bankadress) {
        this.bankadress = bankadress;
    }

    public String getBenificaryname() {
        return benificaryname;
    }

    public void setBenificaryname(String benificaryname) {
        this.benificaryname = benificaryname;
    }

    public String getAccounttype() {
        return accounttype;
    }


    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }


    public String getServiceList() {
        return serviceList;
    }

    public void setServiceList(String serviceList) {
        this.serviceList = serviceList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }


    public String getGovtidno() {
        return govtidno;
    }

    public void setGovtidno(String govtidno) {
        this.govtidno = govtidno;
    }

    public String getBankPhoto() {
        return bankPhoto;
    }

    public void setBankPhoto(String bankPhoto) {
        this.bankPhoto = bankPhoto;
    }


    public String getGidFrontPhoto() {
        return gidFrontPhoto;
    }

    public void setGidFrontPhoto(String gidFrontPhoto) {
        this.gidFrontPhoto = gidFrontPhoto;
    }

    public String getGidBackPhoto() {
        return gidBackPhoto;
    }

    public void setGidBackPhoto(String gidBackPhoto) {
        this.gidBackPhoto = gidBackPhoto;
    }

    public String getPhoneNoAdd() {
        return phoneNoAdd;
    }

    public void setPhoneNoAdd(String phoneNoAdd) {
        this.phoneNoAdd = phoneNoAdd;
    }

    public String getWorkImg() {
        return workImg;
    }

    public void setWorkImg(String workImg) {
        this.workImg = workImg;
    }

    public String getBycId() {
        return bycId;
    }

    public void setBycId(String bycId) {
        this.bycId = bycId;
    }

    public HomeVendorModel(String regno, String ownerName, String email, String phoneNo, String phoneAdd, String city, String address,
                           String bankname, String bankadress, String benificaryname, String accountNo, String accounttype,
                           String ifsccode, String serviceList, String time, String status,
                           String docType, String govtidno, String bankPhoto, String gidFrontPhoto, String gidBackPhoto, String workImg, String bycId) {
        this.regno = regno;
        this.ownerName = ownerName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.phoneNoAdd = phoneNoAdd;
        this.city = city;
        this.address = address;
        this.bankname = bankname;
        this.bankadress = bankadress;
        this.benificaryname = benificaryname;
        this.accountNo = accountNo;
        this.accounttype = accounttype;
        this.ifsccode = ifsccode;

        this.serviceList = serviceList;
        this.time = time;
        this.status = status;
        this.docType = docType;
        this.govtidno = govtidno;

        this.bankPhoto = bankPhoto;
        this.gidFrontPhoto = gidFrontPhoto;
        this.gidBackPhoto = gidBackPhoto;
        this.workImg = workImg;

        this.bycId = bycId;

    }

    private String bycId;
    private String ownerName;
    private String regno;
    private String email;
    private String phoneNo;
    private String phoneNoAdd;
    private String city;
    private String address;
    private String bankname;
    private String bankadress;
    private String benificaryname;

    private String accountNo;
    private String accounttype;
    private String ifsccode;

    private String serviceList;
    private String time;
    private String status;
    private String docType;
    private String govtidno;


    private String bankPhoto;


    private String gidFrontPhoto;
    private String gidBackPhoto;
    private String workImg;




}
