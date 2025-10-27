import { SocialIcon } from 'react-social-icons'
import Projects from './Projects.tsx'

export function About() {

    return (
        <div className="flex flex-col items-center justify-center min-h-[80vh] px-6 text-gray-100">
            <div className="max-w-4xl w-full bg-white/10 backdrop-blur-lg border border-white/20 rounded-2xl shadow-2xl p-8 space-y-6 animate-fade-in-up mt-4">
                <h1 className="text-4xl font-bold text-center text-indigo-300 mb-4">
                    About Me
                </h1>

                <div className="flex flex-col md:flex-row items-center md:items-start gap-10">
                    <div className="flex-shrink-0 flex flex-col items-center md:items-center">
                        <img
                            src="src/assets/selfie.jpeg"
                            alt="Oliver Kastner"
                            className="w-40 h-40 rounded-full border-4 border-indigo-400/50 shadow-lg object-cover hover:scale-105 transition-transform duration-300"
                        />
                        <div className="flex justify-center gap-4 mt-8">
                            <SocialIcon
                                url={"https://github.com/Oli2406"}
                                className="w-10 h-10 bg-white/10 rounded-full transition-transform hover:scale-110"
                                fgColor="#fff"
                                bgColor="transparent"
                            />
                            <SocialIcon
                                url={"https://www.linkedin.com/in/oliver-kastner-507a45250/"}
                                className="w-10 h-10 bg-white/10 rounded-full transition-transform hover:scale-110"
                                fgColor="#fff"
                                bgColor="transparent"
                            />
                        </div>
                    </div>

                    <div className="text-center md:text-left space-y-4">
                        <p className="text-gray-300 leading-relaxed text-lg">
                            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
                            nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam
                            erat, sed diam voluptua. At vero eos et accusam et justo duo
                            dolores et ea rebum. Stet clita kasd gubergren, no sea takimata
                            sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet,
                            consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt
                            ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero
                            eos et accusam et justo duo dolores et ea rebum. Stet clita kasd
                            gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
                        </p>

                        <p className="text-gray-300 leading-relaxed text-lg">
                            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
                            nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam
                            erat, sed diam voluptua. At vero eos et accusam et justo duo dolores
                            et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est
                            Lorem ipsum dolor sit amet.
                        </p>

                        <div className="flex flex-wrap gap-3 mt-6 justify-center md:justify-start">
              <span className="px-4 py-2 bg-indigo-500/30 rounded-full border border-indigo-400/30 text-sm font-medium">
                React & TypeScript
              </span>
                            <span className="px-4 py-2 bg-purple-500/30 rounded-full border border-purple-400/30 text-sm font-medium">
                Tailwind CSS
              </span>
                            <span className="px-4 py-2 bg-pink-500/30 rounded-full border border-pink-400/30 text-sm font-medium">
                Java & Spring Boot
              </span>
                            <span className="px-4 py-2 bg-blue-500/30 rounded-full border border-blue-400/30 text-sm font-medium">
                REST APIs
              </span>
                        </div>
                    </div>
                </div>
            </div>
            <Projects/>
        </div>
    );
}

export default About;
