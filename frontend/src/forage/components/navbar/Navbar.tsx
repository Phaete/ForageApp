import {Link} from "react-router-dom";
import {NavbarProps} from "./NavbarProps.ts";

export default function Navbar(props: Readonly<NavbarProps>) {

	return (
		<nav className={"flex flex-row boxed box-radius-b5 justify-between align-center p-5"}>
			<span className={"flat-button"}>
				<Link to={"/"}>Forage Tracker</Link>
			</span>
			<span>
				<Link to={"/map"}><button type={"button"}>Map</button></Link>
				<Link to={"/wiki"}><button type={"button"}>Wiki</button></Link>
				{props.user ? <button type={"button"} onClick={props.logout}>Logout</button> : <button type={"button"} onClick={props.login}>Login</button>}
			</span>
		</nav>
	);
}