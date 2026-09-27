import { createContext, useContext, useState } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(
        localStorage.getItem("token")
    );

    const [userId, setUserId] = useState(
        localStorage.getItem("userId")
    );

    const login = (jwtToken, loggedInUserId) => {
        localStorage.setItem("token", jwtToken);
        localStorage.setItem("userId", loggedInUserId);

        setToken(jwtToken);
        setUserId(loggedInUserId);
    };

    const logout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("userId");

        setToken(null);
        setUserId(null);
    };

    const isAuthenticated = !!token;

    return (
        <AuthContext.Provider
            value={{
                token,
                userId,
                login,
                logout,
                isAuthenticated,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    return useContext(AuthContext);
};