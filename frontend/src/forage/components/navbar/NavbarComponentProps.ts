import {User} from "../../types/User.ts";

export type NavbarComponentProps = {
	loginGithub: () => void,
	loginGoogle: () => void,
	logout: () => void,
	user: User | null
}