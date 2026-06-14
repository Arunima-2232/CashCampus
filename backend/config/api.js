import axios from 'axios';

export const MAIN_BASE_URL=axios.create({baseURL:"http://localhost:8081/expenseManager"});
export const USER_BASE_URL=axios.create({baseURL:"http://localhost:8081/user"})
