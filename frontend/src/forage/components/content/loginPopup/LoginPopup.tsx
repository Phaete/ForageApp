import {LoginPopupProps} from "./LoginPopupProps.ts"
import {Modal} from "react-bootstrap";

export default function LoginPopup(props: Readonly<LoginPopupProps>) {
	return (
		<Modal
			show={props.show}
			onHide={props.onHide}
			size={"lg"}
			centered>
			<Modal.Header closeButton>
				Login
			</Modal.Header>
			<Modal.Body>
				<p>Sign in with:</p>
				<span>
					<span>
						<button
							onClick={props.loginGithub}>
							{/*<img src={"src/forage/assets/images/github.svg"} alt={"github"} width={"30px"}/>*/}
							Github
						</button>
					</span>
					<span>
						<button
							onClick={props.loginGoogle}>
							{/*<img src={"src/forage/assets/images/google.svg"} alt={"google"} width={"30px"}/>*/}
							Google
						</button>
					</span>
				</span>
			</Modal.Body>
		</Modal>
	)
}