import {Link} from "react-router-dom";
import {NavbarComponentProps} from "./NavbarComponentProps.ts";
import {Container, Nav, Navbar} from "react-bootstrap";
import {useState} from "react";
import LoginPopup from "../content/loginPopup/LoginPopup.tsx";

export default function NavbarComponent(props: Readonly<NavbarComponentProps>) {

	const [loginPrompt, setLoginPrompt] = useState<boolean>(false)

	return (
		<Navbar className={"flex flex-row boxed box-radius-b5 justify-between align-center"}>
			<Container>
				<Navbar.Brand as={Link} to={"/"}>Forage Tracker</Navbar.Brand>
			<Nav>
				<Nav.Link as={Link} to={"/map"}>Map</Nav.Link>
				<Nav.Link as={Link} to={"/wiki"}>Wiki</Nav.Link>
				{props.user ?
					<Nav.Link onClick={props.logout}>Logout</Nav.Link> : <Nav.Link onClick={() => setLoginPrompt(true)}>Login</Nav.Link>
				}
				<LoginPopup
					show={loginPrompt}
					onHide={() => setLoginPrompt(false)}
					loginGithub={props.loginGithub}
					loginGoogle={props.loginGoogle}/>
			</Nav>
			</Container>
		</Navbar>
	);
}