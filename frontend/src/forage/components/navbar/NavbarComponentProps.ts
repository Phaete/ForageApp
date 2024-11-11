import {User} from "../../types/User.ts";

export type NavbarComponentProps = {
	login: () => void,
	logout: () => void,
	user: User | null
}