import {Link} from "react-router-dom";
import {NavbarComponentProps} from "./NavbarComponentProps.ts";
import {Container, Nav, Navbar} from "react-bootstrap";

export default function NavbarComponent(props: Readonly<NavbarComponentProps>) {

	return (
		<Navbar className={"flex flex-row boxed box-radius-b5 justify-between align-center"}>
			<Container>
				<Navbar.Brand as={Link} to={"/"}>Forage Tracker</Navbar.Brand>
			<Nav>
				<Nav.Link as={Link} to={"/map"}>Map</Nav.Link>
				<Nav.Link as={Link} to={"/wiki"}>Wiki</Nav.Link>
				{props.user ?
					<Nav.Link onClick={props.logout}>Logout</Nav.Link> : <Nav.Link onClick={props.login}>Login</Nav.Link>
				}
			</Nav>
			</Container>
		</Navbar>
	);
}