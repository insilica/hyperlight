{
  description = "hyperlight";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixpkgs-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };
  outputs = { self, nixpkgs, flake-utils, ... }@inputs:
    flake-utils.lib.eachDefaultSystem (system:
      with import nixpkgs { inherit system; };
      let
        hyperlight = stdenv.mkDerivation {
          name = "hyperlight";
          src = ./.;

          installPhase = ''
            mkdir -p $out
          '';
        };
      in {
        packages = {
          inherit hyperlight;
          default = hyperlight;
        };
        devShells.default = mkShell { buildInputs = [ clojure rlwrap ]; };
      });
}
