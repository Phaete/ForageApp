import {LandingPageProps} from "./LandingPageProps.ts";
import {Container} from "react-bootstrap";

export default function LandingPage(props: Readonly<LandingPageProps>) {

	function login() {
		props.login()
	}

	return (
		<Container>
			<h1>Discover Nature's Bounty</h1>
			<p>Easily track wild edible plants and mushrooms in your area.</p>
			{props.user ? <button onClick={props.logout}>Logout</button> : <button onClick={login}>Login</button>}
		</Container>
	)
}