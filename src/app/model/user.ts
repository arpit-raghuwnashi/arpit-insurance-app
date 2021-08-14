export class User {
    firstName:any;
    lastName:any;
    email:any;
    phoneNo:any;
    dateOfBirth:any;
    gender:any;
    occupation:any;
    password:any;
    aadharNo:any;
    annualIncome:any;
    education:any;
    ifDiabetic:any;
    ifSmoker:any;
    hasBp:any;
    address:any;
    roleId:any;
    
    constructor(email:any, password:any,first_name:any,last_name:any,phone_no:any,date_of_birth:any,gender:any,occupation:any,aadhar_no:any, education:any, if_diabetic:any,if_smoker:any,has_bp:any,annual_income:any,address:any){
        this.email = email;
        this.password = password;
        this.firstName = first_name;
        this.aadharNo = aadhar_no;
        this.lastName = last_name;
        this.education = education;
        this.phoneNo = phone_no;
        this.dateOfBirth = date_of_birth;
        this.ifDiabetic = if_diabetic;
        this.ifSmoker = if_smoker;
        this.gender = gender;
        this.hasBp = has_bp;
        this.occupation = occupation;
        this.annualIncome = annual_income;
        this.address = address
        this.roleId=3;
    }
}

