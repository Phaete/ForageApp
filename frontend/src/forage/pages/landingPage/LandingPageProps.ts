import {User} from "../../types/User.ts";

export type LandingPageProps = {
	login: () => void
	logout: () => void
	getMe: () => void
	user: User | null
}