export class Nominee {
    firstName:any;
    lastName:any;
    email:any;
    phone:any;
    userId:any;
    
    constructor(email:string, lastName:string,firstName:string,phone:any,userId:any){
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.userId = userId;
        
    }
}
