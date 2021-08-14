export class User {
    constructor(
        public roleId: number,
        public status: boolean,
        public firstName: string,
        public lastName: string,
        public gender: string,
        public email: string,
        public password: string,
        public phoneNo: number,
        public aadharNo: string,
        public education: string,
        public annualIncome:number,
        public dateOfBirth:Date,
        
    ) { }
}

export interface IUser {
    userId: number,
    roleId: number,
    firstName: string,
    lastName: string,
    gender: string,
    email: string,
    phoneNo: number,
    aadharNo: string,
    education: string
    password :string,
    dateOfBirth:Date,
    annualIncome:number,
    status:boolean,
    occupation:string,

}