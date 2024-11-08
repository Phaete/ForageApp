import {User} from "../../types/User.ts";

export type NavbarProps = {
	login: () => void,
	logout: () => void,
	user: User | null
}