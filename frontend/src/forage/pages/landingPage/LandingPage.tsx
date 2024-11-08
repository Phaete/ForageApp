import {LandingPageProps} from "./LandingPageProps.ts";

export default function LandingPage(props: Readonly<LandingPageProps>) {

	function login() {
		props.login()
	}

	return (
		<>
			<p>Please log in for full functionality.</p>
			{props.user ? <button onClick={props.logout}>Logout</button> : <button onClick={login}>Login</button>}
		</>
	)
}