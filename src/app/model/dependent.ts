export class Dependent {
    firstName:string;
    LastName:string;
    email:string;
    age:any;
    phone:any;
    userId:any;
    
    constructor(email:string, lastName:string,firstName:string,phone:any,userId:any,age:any){
        this.email = email;
        this.LastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.age=age;
        this.userId = userId;
    }
    // dependent : Array<{firstName:String,LastName:string,email:string,phone:any,age:any}> =[]
}
